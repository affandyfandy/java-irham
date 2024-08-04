package findo.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import findo.product.data.entity.Product;
import findo.product.data.repository.ProductRepository;
import findo.product.service.ProductService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    final private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
