package findo.lab.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import findo.lab.model.Employee;
import findo.lab.repository.EmployeeRepository;
import findo.lab.service.EmployeeService;
import findo.lab.utils.FileUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Employee findById(String theId) {
        return employeeRepository.findById(theId).orElseThrow();
    }

    @Override
    public void save(Employee theEmployee) {
        if (theEmployee.getId() == null || theEmployee.getId().isEmpty()) {
            theEmployee.setId(UUID.randomUUID().toString());
        }
        employeeRepository.save(theEmployee);
    }

    @Override
    public void deleteById(String theId) {
        employeeRepository.deleteById(theId);
    }

    @Override
    public void uploadCSVFile(MultipartFile file) {
        try {
            List<Employee> employees = FileUtils.getEmployeeFromCSV(file);
            employeeRepository.saveAll(employees);
        } catch (Exception e) {
            throw new RuntimeException("Error when uploading CSV File: " + e.getMessage());
        }
    }

    @Override
    public List<Employee> findPaginated(int page, int size) {
        return employeeRepository.findAll(PageRequest.of(page, size, Sort.by("name").ascending())).getContent();
    }

    @Override
    public long countEmployees() {
        return employeeRepository.count();
    }
}
