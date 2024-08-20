package com.fpt.MidtermG1.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.data.entity.Customer;
import com.fpt.MidtermG1.data.repository.CustomerRepository;
import com.fpt.MidtermG1.data.specification.CustomerSpecification;
import com.fpt.MidtermG1.dto.CustomerDTO;
import com.fpt.MidtermG1.service.impl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setId("1");
        customer.setName("John Doe");
        customer.setPhoneNumber("123456789");
        customer.setStatus(Status.ACTIVE);

        customerDTO = customer.toDTO();
    }

    @Test
    public void testGetCustomerList() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customers = new PageImpl<>(List.of(customer));
        when(customerRepository.findAll(pageable)).thenReturn(customers);

        Page<CustomerDTO> result = customerService.getCustomerList(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("John Doe");
    }

    @Test
    public void testSearchCustomers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customers = new PageImpl<>(List.of(customer));
        when(customerRepository.findAll(any(CustomerSpecification.class), eq(pageable))).thenReturn(customers);

        Page<CustomerDTO> result = customerService.searchCustomers("John", pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("John Doe");
    }

    @Test
    public void testGetCustomerById() {
        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));

        Optional<CustomerDTO> result = customerService.getCusromerById("1");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("John Doe");
    }

    @Test
    public void testAddCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerService.addCustomer(customerDTO);

        assertThat(result.getName()).isEqualTo("John Doe");
    }

    @Test
    public void testEditCustomer() {
        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO updatedDTO = new CustomerDTO();
        updatedDTO.setName("Jane Doe");

        CustomerDTO result = customerService.editCustomer("1", updatedDTO);

        assertThat(result.getName()).isEqualTo("Jane Doe");
    }

    @Test
    public void testEditCustomer_NotFound() {
        when(customerRepository.findById("1")).thenReturn(Optional.empty());

        CustomerDTO updatedDTO = new CustomerDTO();

        assertThrows(RuntimeException.class, () -> {
            customerService.editCustomer("1", updatedDTO);
        });
    }

    @Test
    public void testActivateCustomer() {
        customer.setStatus(Status.INACTIVE);
        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerService.activateCustomer("1");

        assertThat(result.getStatus()).isEqualTo(Status.ACTIVE);
    }

    @Test
    public void testActivateCustomer_AlreadyActive() {
        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));

        assertThrows(RuntimeException.class, () -> {
            customerService.activateCustomer("1");
        });
    }

    @Test
    public void testDeactivateCustomer() {
        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerService.deactivateCustomer("1");

        assertThat(result.getStatus()).isEqualTo(Status.INACTIVE);
    }

    @Test
    public void testDeactivateCustomer_AlreadyInactive() {
        customer.setStatus(Status.INACTIVE);
        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));

        assertThrows(RuntimeException.class, () -> {
            customerService.deactivateCustomer("1");
        });
    }
}
