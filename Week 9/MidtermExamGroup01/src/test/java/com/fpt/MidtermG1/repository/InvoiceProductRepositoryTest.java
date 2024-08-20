package com.fpt.MidtermG1.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.data.entity.Customer;
import com.fpt.MidtermG1.data.entity.Invoice;
import com.fpt.MidtermG1.data.entity.InvoiceProduct;
import com.fpt.MidtermG1.data.entity.Product;
import com.fpt.MidtermG1.data.repository.InvoiceProductRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class InvoiceProductRepositoryTest {

    @Autowired
    private InvoiceProductRepository invoiceProductRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Mock
    private EntityManager em;

    private Invoice invoice;
    private Product product;
    private InvoiceProduct invoiceProduct;

    @BeforeEach
    public void setUp() {
        // Initialize the test data
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setPhoneNumber("+123456789");
        customer.setStatus(Status.ACTIVE);
        entityManager.persist(customer);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        invoice = new Invoice();
        invoice.setInvoiceAmount(new BigDecimal(500));
        invoice.setInvoiceDate(timestamp);
        invoice.setCustomer(customer);
        entityManager.persist(invoice);

        product = new Product();
        product.setId(1);
        product.setName("Product A");
        product.setPrice(new BigDecimal(100));
        entityManager.persist(product);

        invoiceProduct = new InvoiceProduct();
        invoiceProduct.setInvoice(invoice);
        invoiceProduct.setProduct(product);
        entityManager.persist(invoiceProduct);
    }

    @Test
    @Transactional
    public void testDeleteByInvoice() {
        // Given an existing invoiceProduct
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAll();
        assertThat(invoiceProducts).isNotEmpty();

        // When deleteByInvoice is called
        invoiceProductRepository.deleteByInvoice(invoice);

        // Then the invoiceProduct should be deleted
        invoiceProducts = invoiceProductRepository.findAll();
        assertThat(invoiceProducts).isEmpty();
    }

    @Test
    public void testFindAllWithDetails() {
        // When findAllWithDetails is called
        List<InvoiceProduct> result = invoiceProductRepository.findAllWithDetails();

        // Then the result should contain the invoiceProduct with fetched details
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getInvoice()).isEqualTo(invoice);
        assertThat(result.get(0).getProduct()).isEqualTo(product);
    }
}
