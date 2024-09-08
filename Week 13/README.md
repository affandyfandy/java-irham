# Spring Security with JWT
This guide provides an overview of how to implement security for your microservices using JSON Web Tokens (JWT) and asymmetric keys (RSA).

## Asymmetric Key Pair
### Create RSA Key
To create a pair of RSA keys (public and private), use the following commands:

```sh
# Generate a private key
openssl genpkey -algorithm RSA -out private.pem -pkeyopt rsa_keygen_bits:2048

# Generate a public key from the private key
openssl rsa -pubout -in private.pem -out public.pem
```

### Store RSA Key
1. **Create a directory**: Create a directory `src/main/resources/keys` in your [auth-service](demo-project/auth/) & [gateway-service](demo-project/auth/) project.
2. **Place your keys**: Move `public.pem` and `private.pem` to the `src/main/resources/keys` directory.

## Auth Service Configuration
### Dependencies
Add the following dependencies to your [pom.xml](demo-project/auth/pom.xml)
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-jose</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-jwt</artifactId>
</dependency>
```

### PemUtils
Create a utility class [PemUtils](demo-project/auth/src/main/java/findo/auth/util/PemUtils.java) for reading public and private keys from `PEM files`.

```java
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class PemUtils {

    public static PrivateKey readPrivateKey(InputStream privateKeyStream) throws Exception {
        String privateKeyPEM = new String(privateKeyStream.readAllBytes());
        privateKeyPEM = privateKeyPEM
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static RSAPublicKey readPublicKey(InputStream publicKeyStream) throws Exception {
        String publicKeyPEM = new String(publicKeyStream.readAllBytes());
        publicKeyPEM = publicKeyPEM
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }
}
```

### [SecurityConfig](demo-project/auth/src/main/java/findo/auth/config/SecurityConfig.java)
Configure Spring Security for JWT in the Auth Service.
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disabling CSRF for stateless API
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/v1/auth/register", "/api/v1/auth/login").permitAll() // Allow public access to register and login
                    .anyRequest().authenticated() // Protect all other endpoints
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())); // Enable JWT-based authentication for protected endpoints
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        // Loading public key from the PEM file
        ClassPathResource resource = new ClassPathResource("keys/public.pem");
        RSAPublicKey publicKey = PemUtils.readPublicKey(resource.getInputStream());
        return NimbusJwtDecoder.withPublicKey(publicKey).build(); // Building the decoder with the public key
    }

    @Bean
    public JwtEncoder jwtEncoder() throws Exception {
        // Load the private key from the PEM file
        ClassPathResource privateKeyResource = new ClassPathResource("keys/private.pem");
        PrivateKey privateKey = PemUtils.readPrivateKey(privateKeyResource.getInputStream());

        // Load the public key again from the PEM file
        ClassPathResource publicKeyResource = new ClassPathResource("keys/public.pem");
        RSAPublicKey publicKey = PemUtils.readPublicKey(publicKeyResource.getInputStream());

        // Create the RSAKey using both the public and private keys
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();

        // Configure the JWT encoder using the RSAKey
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
```

## Auth API Implementation
### [User Entity](demo-project/auth/src/main/java/findo/auth/data/entity/User.java)
Define the User entity class.
```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;
}
```

### [UserRepository](demo-project/auth/src/main/java/findo/auth/data/repository/UserRepository.java)
Create a repository interface for User entity.
```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
```

### [AuthenticationService](demo-project/auth/src/main/java/findo/auth/service/impl/AuthenticationServiceImpl.java)
Implement authentication logic.
```java
@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    
    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;
    final private JwtEncoder jwtEncoder;
    final private AuthenticationManager authenticationManager;
    
    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Optional<String> login(AuthDTO authRequest) {
        try {
            // User authentication
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Get user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Claim JWT
            Instant now = Instant.now();
            long expiry = 36000L; // Token valid for 10 hours

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiry))
                    .subject(userDetails.getUsername())
                    .claim("roles", userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority).toList())
                    .build();

            // Encode JWT using claim
            String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return Optional.ofNullable(token);
        } catch (AuthenticationException e) {
            return null;
        }
    }    
}
```

### [AuthenticationController](demo-project/auth/src/main/java/findo/auth/controller/AuthenticationController.java)
Create a controller for authentication endpoints.
```java
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    final private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthDTO user) {
        User userData = new User();
        userData.setUsername(user.getUsername());
        userData.setPassword(user.getPassword());
        userData.setRole("BASIC");
        
        authenticationService.saveUser(userData);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO authRequest) {
        Optional<String> token = authenticationService.login(authRequest);
        if (token != null) {
            return ResponseEntity.ok(token.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
```


## Gateway Service Configuration
### Dependencies
Add the following dependencies to your [pom.xml](demo-project/gateway/pom.xml)
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-jose</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-jwt</artifactId>
</dependency>
```

### [SecurityConfig](demo-project/gateway/src/main/java/findo/gateway/config/SecurityConfig.java)
Configure Spring Security for JWT in the Gateway Service.

```java
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless API
            .authorizeExchange(exchange -> 
                exchange
                    .pathMatchers("/api/v1/auth/**").permitAll() // Allow public access to auth endpoints
                    .anyExchange().authenticated() // Protect all other endpoints
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())); // Enable JWT-based authentication
        
        return http.build();
    }
}
```

## Test Using Postman
1. Start `auth`, `invoice`, `product`, and `gateway` project.
2. **Register a User**: Send a `POST` request to `http://localhost:8080/api/v1/auth/register` with user details in the body.
![img](img/register.png)

3. **Login**: Send a `POST` request to `http://localhost:8080/api/v1/auth/login` with username and password. Receive a JWT token in response.
![img](img/login.png)

4. **Access Protected Endpoints**: Use the JWT token obtained from the login request to access protected endpoints on the Gateway Service. Add the `Authorization` header with the value `<your-jwt-token>`.
![img](img/get-success.png)

5. **Unauthorized**: Make sure that the response is `unauthorized` when you accessing the protected endpoints.
![img](img/get-unauthorized.png)