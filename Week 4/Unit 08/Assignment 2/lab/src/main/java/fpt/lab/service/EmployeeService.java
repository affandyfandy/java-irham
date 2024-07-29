package fpt.lab.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.lab.model.Employee;
import fpt.lab.repository.EmployeeRepository;

@Service
public class EmployeeService {
    
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional(transactionManager = "employeeTransactionManager")
    public int save(Employee employee) {
        try {
            return repository.save(employee);
        } catch (DataAccessException e) {
            throw e;
        }
    }

    @Transactional(transactionManager = "employeeTransactionManager")
    public int update(Employee employee) {
        try {
            return repository.update(employee);
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public Employee findById(String id) {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw e;
        }
    }

    @Transactional(transactionManager = "employeeTransactionManager")
    public int deleteById(String id) {
        try {
            return repository.deleteById(id);
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public List<Employee> findAll() {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public List<Employee> search(String query) {
        try {
            return repository.search(query);
        } catch (DataAccessException e) {
            throw e;
        }
    }
}
