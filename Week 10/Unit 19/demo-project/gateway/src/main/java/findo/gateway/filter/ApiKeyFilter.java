package findo.gateway.filter;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class ApiKeyFilter extends AbstractGatewayFilterFactory<ApiKeyFilter.Config> {

    final private WebClient.Builder webClientBuilder;

    @Autowired
    public ApiKeyFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String apiKey = exchange.getRequest().getHeaders().getFirst("api-key");

            if (apiKey == null) {
                return unauthorizedResponse(exchange, "API key is missing");
            }

            // boolean isValid = webClientBuilder.build()
            //         .method(HttpMethod.GET)
            //         .uri("http://localhost:8083/auth/validate-api-key?apiKey=" + apiKey)
            //         .retrieve()
            //         .bodyToMono(Boolean.class)
            //         .block();

            // if (!isValid) {
            //     exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            //     return exchange.getResponse().setComplete();
            // }

            // return chain.filter(exchange);

            return webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8083/auth/validate?apiKey=" + apiKey)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .flatMap(isValid -> {
                        if (Boolean.TRUE.equals(isValid)) {
                            return chain.filter(exchange);
                        } else {
                            return unauthorizedResponse(exchange, "API key is invalid");
                        }
                    });
        };
    }

    public static class Config {
        
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
    }
}
