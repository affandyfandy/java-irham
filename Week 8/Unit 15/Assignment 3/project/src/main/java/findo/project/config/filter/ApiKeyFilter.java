package findo.project.config.filter;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import findo.project.data.entity.ApiKey;
import findo.project.data.repository.ApiKeyRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {
    
    final private ApiKeyRepository apiKeyRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String apiKey = request.getHeader("api-key");

        response.setHeader("source", "fpt-software");
        response.setHeader("timestamp", LocalDateTime.now().toString());

        if (apiKey == null || !apiKeyRepository.findByApiKey(apiKey).isPresent()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
            return;
        }

        ApiKey apiKeyEntity = apiKeyRepository.findByApiKey(apiKey).get();
        apiKeyEntity.setLastUsed(LocalDateTime.now());
        apiKeyRepository.save(apiKeyEntity);
        response.setHeader("username", apiKeyEntity.getUsername());

        filterChain.doFilter(request, response);
    }
}
