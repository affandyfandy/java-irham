package com.fpt.MidtermG1.data.repository;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    Page<Product> findAll(Pageable pageable);

    Page<Product> findByNameContaining(String name, Pageable pageable);

    Page<Product> findByStatus(Status status, Pageable pageable);

    Page<Product> findByNameContainingAndStatus(String name, Status status, Pageable pageable);
}