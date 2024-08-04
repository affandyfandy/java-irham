package findo.invoice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class AppConfig {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("user", "password");
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("user", "password"));
        return restTemplate;
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                    .defaultHeaders(headers -> headers.setBasicAuth("user", "password"));
    }
}
