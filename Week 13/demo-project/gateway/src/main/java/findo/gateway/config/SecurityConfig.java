package findo.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

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

