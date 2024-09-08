package findo.auth.service.impl;

import java.time.Instant;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import findo.auth.data.entity.User;
import findo.auth.data.repository.UserRepository;
import findo.auth.dto.AuthDTO;
import findo.auth.service.AuthenticationService;
import lombok.AllArgsConstructor;

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
