package com.fpt.MidtermG1.controller;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.dto.CustomerDTO;
import com.fpt.MidtermG1.service.CustomerService;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private CustomerDTO customerDTO;
    private String customerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customerId = UUID.randomUUID().toString();
        customerDTO = CustomerDTO.builder()
                .id(customerId)
                .name("John Doe")
                .phoneNumber("+123456789")
                .status(Status.ACTIVE)
                .build();
    }

    @Test
    void testGetCustomerList() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerDTO> customerPage = new PageImpl<>(Collections.singletonList(customerDTO));
        when(customerService.getCustomerList(pageable)).thenReturn(customerPage);

        ResponseEntity<Page<CustomerDTO>> response = customerController.getCustomerList(0, 10, "");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    void testGetCustomerListWithKeyword() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerDTO> customerPage = new PageImpl<>(Collections.singletonList(customerDTO));
        when(customerService.searchCustomers(anyString(), any(Pageable.class))).thenReturn(customerPage);

        ResponseEntity<Page<CustomerDTO>> response = customerController.getCustomerList(0, 10, "John");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    void testGetCustomerListNotFound() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerDTO> customerPage = Page.empty();
        when(customerService.getCustomerList(pageable)).thenReturn(customerPage);

        ResponseEntity<Page<CustomerDTO>> response = customerController.getCustomerList(0, 10, "");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetCustomerById() {
        when(customerService.getCusromerById(anyString())).thenReturn(Optional.of(customerDTO));

        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customerDTO, response.getBody());
    }

    @Test
    void testGetCustomerByIdNotFound() {
        when(customerService.getCusromerById(anyString())).thenReturn(Optional.empty());

        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(customerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddCustomer() {
        when(customerService.addCustomer(any(CustomerDTO.class))).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = customerController.addCustomer(customerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customerDTO, response.getBody());
    }

    @Test
    void testEditCustomer() {
        when(customerService.editCustomer(anyString(), any(CustomerDTO.class))).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = customerController.editCustomer(customerId, customerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customerDTO, response.getBody());
    }

    @Test
    void testActivateCustomer() {
        when(customerService.activateCustomer(anyString())).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = customerController.activateStatus(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customerDTO, response.getBody());
    }

    @Test
    void testDeactivateCustomer() {
        when(customerService.deactivateCustomer(anyString())).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = customerController.deactivateStatus(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customerDTO, response.getBody());
    }
}