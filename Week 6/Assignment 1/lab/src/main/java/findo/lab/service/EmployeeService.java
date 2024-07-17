package findo.lab.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import findo.lab.data.entity.Employee;

public interface EmployeeService {
    Page<Employee> findAll(Pageable pageable);
    Optional<Employee> findById(Integer empNo);
    Employee save(Employee employee);
    void deleteById(Integer empNo);
}
