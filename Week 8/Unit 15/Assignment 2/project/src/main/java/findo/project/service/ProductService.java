package findo.project.service;

import java.util.List;
import java.util.Optional;

import findo.project.data.entity.Product;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(String id);
    Product createProduct(Product product);
    Optional<Product> updateProduct(String id, Product product);
    void deleteProduct(String id);
}
