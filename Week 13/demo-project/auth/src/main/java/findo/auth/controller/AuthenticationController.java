package findo.auth.controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import findo.auth.data.entity.User;
import findo.auth.dto.AuthDTO;
import findo.auth.service.AuthenticationService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    final private AuthenticationService authenticationService;
    final private JwtEncoder jwtEncoder;
    final private AuthenticationManager authenticationManager;

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

            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
