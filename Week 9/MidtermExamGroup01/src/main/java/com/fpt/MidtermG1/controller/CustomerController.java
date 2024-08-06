package com.fpt.MidtermG1.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.MidtermG1.dto.CustomerDTO;
import com.fpt.MidtermG1.service.CustomerService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> getCustomerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "keyword", required = false) String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerDTO> customers;

        if (keyword == null || keyword.isBlank()) {
            customers = customerService.getCustomerList(pageable);
        } else {
            customers = customerService.searchCustomers(keyword, pageable);
        }

        if (customers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customers);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") String id) {
        Optional<CustomerDTO> customerOpt = customerService.getCusromerById(id);

        if (customerOpt.isPresent()) {
            return ResponseEntity.ok(customerOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody @Valid CustomerDTO body) {
        return ResponseEntity.ok(customerService.addCustomer(body));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomerDTO> editCustomer(@PathVariable("id") String id,
            @RequestBody @Valid CustomerDTO body) {
        return ResponseEntity.ok(customerService.editCustomer(id, body));
    }

    @PutMapping(value = "/activate/{id}")
    public ResponseEntity<CustomerDTO> activateStatus(@PathVariable("id") String id) {
        return ResponseEntity.ok(customerService.activateCustomer(id));
    }

    @PutMapping(value = "/deactivate/{id}")
    public ResponseEntity<CustomerDTO> deactivateStatus(@PathVariable("id") String id) {
        return ResponseEntity.ok(customerService.deactivateCustomer(id));
    }
}
