package findo.lab.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import findo.lab.data.model.Employee;
import findo.lab.data.repository.EmployeeRepository;
import findo.lab.dto.EmployeeDTO;
import findo.lab.exception.EmployeeNotFoundException;
import findo.lab.mapper.EmployeeMapper;
import findo.lab.service.EmployeeService;
import jakarta.validation.Valid;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::employeeToEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> searchEmployees(String query) {
        return employeeRepository.searchEmployees(query).stream()
                .map(employeeMapper::employeeToEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeById(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        return employeeMapper.employeeToEmployeeDTO(employee);
    }

    @Override
    public EmployeeDTO createEmployee(@Valid EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.employeeToEmployeeDTO(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(String id, @Valid EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setDob(employeeDTO.getDob());
        existingEmployee.setAddress(employeeDTO.getAddress());
        existingEmployee.setDepartment(employeeDTO.getDepartment());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setPhone(employeeDTO.getPhone());
        employeeRepository.save(existingEmployee);
        return employeeMapper.employeeToEmployeeDTO(existingEmployee);
    }

    @Override
    public void deleteEmployee(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }
}
