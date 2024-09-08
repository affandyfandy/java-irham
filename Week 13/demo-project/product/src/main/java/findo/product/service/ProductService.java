package findo.product.service;

import java.util.List;
import java.util.Optional;

import findo.product.data.entity.Product;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(int id);
    Product saveProduct(Product product);
}
