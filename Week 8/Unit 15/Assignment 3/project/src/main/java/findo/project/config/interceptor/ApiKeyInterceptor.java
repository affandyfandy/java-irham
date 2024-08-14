package findo.project.config.interceptor;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import findo.project.data.entity.ApiKey;
import findo.project.data.repository.ApiKeyRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ApiKeyInterceptor implements HandlerInterceptor {
    
    private final ApiKeyRepository apiKeyRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String apiKey = request.getHeader("api-key");

        Optional<ApiKey> apiKeyOptional = apiKeyRepository.findByApiKey(apiKey);

        if (apiKeyOptional.isPresent()) {
            ApiKey apiKeyEntity = apiKeyOptional.get();
            apiKeyEntity.setLastUsed(LocalDateTime.now());
            apiKeyRepository.save(apiKeyEntity);

            response.setHeader("username", apiKeyEntity.getUsername());
        }

        return true;
    }
}
