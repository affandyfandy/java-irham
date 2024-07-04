package fpt.lab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpt.lab.model.Employee;
import fpt.lab.repository.EmployeeRepository;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository repository;

    public int save(Employee employee) {
        return repository.save(employee);
    }

    public int update(Employee employee) {
        return repository.update(employee);
    }

    public Employee findById(String id) {
        return repository.findById(id);
    }

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
