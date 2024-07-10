package findo.lab.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import findo.lab.model.Employee;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(String theId);
    void save(Employee theEmployee);
    void deleteById(String theId);
    void uploadCSVFile(MultipartFile file);

    List<Employee> findPaginated(int page, int size);
    long countEmployees();
}
