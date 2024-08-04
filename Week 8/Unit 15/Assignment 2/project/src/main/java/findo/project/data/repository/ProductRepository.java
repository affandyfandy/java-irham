package findo.project.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import findo.project.data.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
