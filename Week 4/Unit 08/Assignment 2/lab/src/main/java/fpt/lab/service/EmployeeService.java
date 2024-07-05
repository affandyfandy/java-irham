package fpt.lab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.lab.model.Employee;
import fpt.lab.repository.EmployeeRepository;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository repository;

    @Transactional(transactionManager = "employeeTransactionManager")
    public int save(Employee employee) {
        return repository.save(employee);
    }

    @Transactional(transactionManager = "employeeTransactionManager")
    public int update(Employee employee) {
        return repository.update(employee);
    }

    public Employee findById(String id) {
        return repository.findById(id);
    }

    @Transactional(transactionManager = "employeeTransactionManager")
    public int deleteById(String id) {
        return repository.deleteById(id);
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public List<Employee> search(String query) {
        return repository.search(query);
    }
}
