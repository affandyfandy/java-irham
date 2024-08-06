package com.fpt.MidtermG1.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.data.entity.Customer;
import com.fpt.MidtermG1.data.repository.CustomerRepository;
import com.fpt.MidtermG1.data.specification.CustomerSpecification;
import com.fpt.MidtermG1.dto.CustomerDTO;
import com.fpt.MidtermG1.exception.ResourceNotFoundException;
import com.fpt.MidtermG1.service.CustomerService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Page<CustomerDTO> getCustomerList(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(Customer::toDTO);
    }

    @Override
    public Page<CustomerDTO> searchCustomers(String keyword, Pageable pageable) {
        CustomerSpecification specification = new CustomerSpecification(keyword);
        Page<Customer> customers = customerRepository.findAll(specification, pageable);
        return customers.map(Customer::toDTO);
    }

    @Override
    public Optional<CustomerDTO> getCusromerById(String id) {
        return customerRepository.findById(id).map(Customer::toDTO);
    }

    @Override
    public CustomerDTO addCustomer(@Valid CustomerDTO body) {
        Customer response = customerRepository.save(body.toEntity());
        return response.toDTO();
    }

    @Override
    public CustomerDTO editCustomer(String id, @Valid CustomerDTO body) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            if (customer.getName() != null) {
                customer.setName(body.getName());
            }

            if (customer.getPhoneNumber() != null) {
                customer.setPhoneNumber(body.getPhoneNumber());
            }

            if (customer.getStatus() != null) {
                customer.setStatus(body.getStatus());
            }

            customer = customerRepository.save(customer);
            return customer.toDTO();
        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }

    @Override
    public CustomerDTO activateCustomer(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        if (customer.getStatus() == Status.INACTIVE) {
            customer.setStatus(Status.ACTIVE);
        } else {
            throw new RuntimeException("Customer status already " + customer.getStatus());
        }

        return customerRepository.save(customer).toDTO();
    }

    @Override
    public CustomerDTO deactivateCustomer(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        if (customer.getStatus() == Status.ACTIVE) {
            customer.setStatus(Status.INACTIVE);
        } else {
            throw new RuntimeException("Customer status already " + customer.getStatus());
        }

        return customerRepository.save(customer).toDTO();
    }
}
