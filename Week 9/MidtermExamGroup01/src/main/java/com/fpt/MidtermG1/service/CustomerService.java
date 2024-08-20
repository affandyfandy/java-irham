package com.fpt.MidtermG1.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpt.MidtermG1.dto.CustomerDTO;

public interface CustomerService {
    Page<CustomerDTO> getCustomerList(Pageable pageable);
    Page<CustomerDTO> searchCustomers(String keyword, Pageable pageable);
    Optional<CustomerDTO> getCusromerById(String id);
    CustomerDTO activateCustomer(String id);
    CustomerDTO deactivateCustomer(String id);
    CustomerDTO addCustomer(CustomerDTO body);
    CustomerDTO editCustomer(String id, CustomerDTO body);
}
