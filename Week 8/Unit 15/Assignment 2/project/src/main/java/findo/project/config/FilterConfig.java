package findo.project.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import findo.project.config.filter.ApiKeyFilter;
import findo.project.data.repository.ApiKeyRepository;

@Configuration
public class FilterConfig {

    @Bean
    public ApiKeyFilter apiKeyFilter(ApiKeyRepository apiKeyRepository) {
        return new ApiKeyFilter(apiKeyRepository);
    }

    @Bean
    public FilterRegistrationBean<ApiKeyFilter> apiKeyFilterRegistration(ApiKeyFilter apiKeyFilter) {
        FilterRegistrationBean<ApiKeyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(apiKeyFilter);
        registrationBean.addUrlPatterns("/api/*"); // Apply filter to all API endpoints
        return registrationBean;
    }
}
