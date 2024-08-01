package findo.invoice.data.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import findo.invoice.config.AppConfig;
import findo.invoice.dto.ProductDTO;

@FeignClient(name="product-services", url="http://localhost:8081/api/v1", configuration=AppConfig.class)
public interface ProductClient {
    @GetMapping("/products/{id}")
    ProductDTO getProductById(@PathVariable("id") int id);
}
