package findo.product.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import findo.product.data.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
