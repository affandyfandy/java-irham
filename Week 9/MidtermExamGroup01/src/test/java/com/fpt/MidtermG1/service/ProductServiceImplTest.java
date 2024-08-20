package com.fpt.MidtermG1.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.data.entity.Product;
import com.fpt.MidtermG1.data.repository.ProductRepository;
import com.fpt.MidtermG1.dto.ProductDTO;
import com.fpt.MidtermG1.exception.ResourceNotFoundException;
import com.fpt.MidtermG1.service.impl.ProductServiceImpl;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        product = Product.builder()
                .id(1)
                .name("Product A")
                .price(BigDecimal.valueOf(100.0))
                .status(Status.ACTIVE)
                .createdTime(Timestamp.from(Instant.now()))
                .updatedTime(Timestamp.from(Instant.now()))
                .build();

        productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus())
                .createdTime(product.getCreatedTime())
                .updatedTime(product.getUpdatedTime())
                .build();
    }

    @Test
    public void testListAllProduct() {
        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));
        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(productPage);

        Page<ProductDTO> result = productService.listAllProduct(Pageable.unpaged(), "");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(productDTO, result.getContent().get(0));
    }

    @Test
    public void testFindProductById() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        Optional<ProductDTO> result = productService.findProductById(product.getId());

        assertTrue(result.isPresent());
        assertEquals(productDTO, result.get());
    }

    @Test
    public void testFindProductById_NotFound() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.findProductById(product.getId());
        });

        assertEquals("Product not found for this id : 1", exception.getMessage());
    }

    @Test
    public void testSaveProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO result = productService.saveProduct(productDTO);

        assertNotNull(result);
        assertEquals(productDTO, result);
    }

    @Test
    public void testUpdateProduct() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO updatedDTO = ProductDTO.builder()
                .id(product.getId())
                .name("Updated Product")
                .price(BigDecimal.valueOf(150.0))
                .status(Status.ACTIVE)
                .createdTime(product.getCreatedTime())
                .updatedTime(product.getUpdatedTime())
                .build();

        Optional<ProductDTO> result = productService.updateProduct(product.getId(), updatedDTO);

        assertTrue(result.isPresent());
        assertEquals(updatedDTO.getName(), result.get().getName());
        assertEquals(updatedDTO.getPrice(), result.get().getPrice());
    }

    @Test
    public void testUpdateProduct_NotFound() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(product.getId(), productDTO);
        });

        assertEquals("Product not found for this id : 1", exception.getMessage());
    }

    @Test
    public void testActivateProduct() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        product.setStatus(Status.INACTIVE);

        ProductDTO result = productService.activateProduct(product.getId());

        assertNotNull(result);
        assertEquals(Status.ACTIVE, result.getStatus());
    }

    @Test
    public void testActivateProduct_AlreadyActive() {
        Product activeProduct = product;
        activeProduct.setStatus(Status.ACTIVE);
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(activeProduct));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.activateProduct(activeProduct.getId());
        });

        assertEquals("Product status already ACTIVE", exception.getMessage());
    }

    @Test
    public void testDeactivateProduct() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO result = productService.deactivateProduct(product.getId());

        assertNotNull(result);
        assertEquals(Status.INACTIVE, result.getStatus());
    }

    @Test
    public void testDeactivateProduct_AlreadyInactive() {
        Product inactiveProduct = product;
        inactiveProduct.setStatus(Status.INACTIVE);
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(inactiveProduct));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.deactivateProduct(inactiveProduct.getId());
        });

        assertEquals("Product status already INACTIVE", exception.getMessage());
    }
}
