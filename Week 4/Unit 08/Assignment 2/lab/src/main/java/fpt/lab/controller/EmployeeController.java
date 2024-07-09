package fpt.lab.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpt.lab.model.ApiResponse;
import fpt.lab.model.Employee;
import fpt.lab.service.EmployeeService;


@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {
    
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<Employee>> getEmployees(@RequestParam(required = false) String query) {
        List<Employee> employees;
        if (query != null) {
            employees = service.search(query);
        } else {
            employees = service.findAll();
        }
        return new ApiResponse<>(HttpStatus.OK.value(), "Employee list fetched successfully.", employees);
    }

    @PostMapping
    public ApiResponse<Employee> addEmployee(@RequestBody Employee employee) {
        service.save(employee);
        return new ApiResponse<>(HttpStatus.OK.value(), "Employee created successfully.", employee);
    }

    @PutMapping
    public ApiResponse<Employee> updateEmployee(@RequestBody Employee employee) {
        service.update(employee);
        return new ApiResponse<>(HttpStatus.OK.value(), "Employee updated successfully.", employee);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteEmployeeById(@PathVariable String id) {
        service.deleteById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Employee deleted successfully.", null);
    }

    @GetMapping("/{id}")
    public ApiResponse<Employee> getEmployeeById(@PathVariable String id) {
        Employee employee = service.findById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Employee fetched successfully.", employee);
    }
}
