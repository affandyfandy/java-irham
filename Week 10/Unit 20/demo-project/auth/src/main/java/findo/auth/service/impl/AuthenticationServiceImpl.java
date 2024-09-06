package findo.auth.service.impl;

import org.springframework.stereotype.Service;

import findo.auth.data.repository.ApiKeyRepository;
import findo.auth.service.AuthenticationService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    
    final private ApiKeyRepository apiKeyRepository;

    @Override
    public boolean isValidApiKey(String apiKey) {
        return apiKeyRepository.findByApiKey(apiKey).isPresent();
    }
}
