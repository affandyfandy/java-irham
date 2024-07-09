package findo.lab.service;

import java.util.List;

import findo.lab.model.Employee;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(int theId);
    void save(Employee theEmployee);
    void deleteById(int theId);
}
