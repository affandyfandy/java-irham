package com.findo.employee_crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.findo.employee_crud.model.Employee;
import com.findo.employee_crud.repository.EmployeeRepository;
import com.findo.employee_crud.utils.FileUtils;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/v1/employee")
@AllArgsConstructor
public class EmployeeController {
    
    @Autowired
    private final EmployeeRepository repository;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> response = repository.findAll();
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") String id) {
        Optional<Employee> response = repository.findById(id);
        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        }
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee body) {
        Optional<Employee> request = repository.findById(body.getId());
        if (request.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repository.save(body));
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<Employee> updateEmployee(
        @PathVariable(value = "id") String id,
        @RequestBody Employee employeeForm) {
        Optional<Employee> employeeOpt = repository.findById(id);
        if(employeeOpt.isPresent()) {
            Employee contact = employeeOpt.get();
            if (employeeForm.getName() != null) {
                contact.setName(employeeForm.getName());
            }
            if (employeeForm.getDepartment() != null) {
                contact.setDepartment(employeeForm.getDepartment());
            }

            Employee updatedEmployee = repository.save(contact);
            return ResponseEntity.ok(updatedEmployee);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable(value = "id") String id) {
        Optional<Employee> employeeOpt = repository.findById(id);
        if(employeeOpt.isPresent()) {
            repository.delete(employeeOpt.get());
            return ResponseEntity.ok(employeeOpt.get());

        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<List<Employee>> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        List<Employee> employees = FileUtils.getEmployeeFromCSV(file);

        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        repository.saveAll(employees);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-department")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@RequestParam("department") String department) {
        List<Employee> employees = repository.findByDepartment(department);
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }
}
