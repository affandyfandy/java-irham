package findo.lab.service;

import java.util.List;

import findo.lab.dto.EmployeeDTO;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    List<EmployeeDTO> searchEmployees(String query);
    EmployeeDTO getEmployeeById(String id);
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(String id, EmployeeDTO employeeDTO);
    void deleteEmployee(String id);
}
