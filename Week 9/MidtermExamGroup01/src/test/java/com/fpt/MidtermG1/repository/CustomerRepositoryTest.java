package com.fpt.MidtermG1.repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.data.entity.Customer;
import com.fpt.MidtermG1.data.repository.CustomerRepository;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        // Create and save a new Customer entity
        customer = Customer.builder()
                .name("John Doe")
                .phoneNumber("+123456789")
                .status(Status.ACTIVE)
                .createdTime(Timestamp.from(Instant.now()))
                .updatedTime(Timestamp.from(Instant.now()))
                .build();

        customerRepository.save(customer);
    }

    @Test
    public void testFindById() {
        Optional<Customer> found = customerRepository.findById(customer.getId());
        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(customer);
    }

    @Test
    public void testFindAll() {
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(1).contains(customer);
    }

    @Test
    public void testSaveNewCustomer() {
        Customer newCustomer = Customer.builder()
                .name("Jane Doe")
                .phoneNumber("+987654321")
                .status(Status.ACTIVE)
                .createdTime(Timestamp.from(Instant.now()))
                .updatedTime(Timestamp.from(Instant.now()))
                .build();

        Customer saved = customerRepository.save(newCustomer);
        assertThat(saved).isEqualTo(newCustomer);

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(2).contains(newCustomer, customer);
    }

    @Test
    public void testUpdateCustomer() {
        // Modify existing entity
        customer.setName("John Smith");
        customer.setPhoneNumber("+111111111");
        customer.setStatus(Status.INACTIVE);

        // Update the existing customer
        Customer updated = customerRepository.save(customer);
        assertThat(updated.getName()).isEqualTo("John Smith");
        assertThat(updated.getPhoneNumber()).isEqualTo("+111111111");
        assertThat(updated.getStatus()).isEqualTo(Status.INACTIVE);
    }

    @Test
    public void testDeleteCustomer() {
        customerRepository.delete(customer);
        Optional<Customer> deleted = customerRepository.findById(customer.getId());
        assertThat(deleted).isNotPresent();
    }
}