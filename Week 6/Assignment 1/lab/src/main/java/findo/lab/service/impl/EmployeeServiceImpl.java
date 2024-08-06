package findo.lab.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import findo.lab.data.criteria.EmployeeSearchCriteria;
import findo.lab.data.entity.Employee;
import findo.lab.data.repository.EmployeeRepository;
import findo.lab.data.repository.EmployeeSpecification;
import findo.lab.dto.EmployeeDTO;
import findo.lab.service.EmployeeService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(Employee::toDTO);
    }

    @Override
    public Page<EmployeeDTO> searchEmployees(EmployeeSearchCriteria criteria, Pageable pageable) {
        EmployeeSpecification specification = new EmployeeSpecification(criteria);
        Page<Employee> employees = employeeRepository.findAll(specification, pageable);
        return employees.map(Employee::toDTO);
    }

    @Override
    public Optional<EmployeeDTO> findById(Integer empNo) {
        return employeeRepository.findById(empNo).map(Employee::toDTO);
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employee) {
        Employee body = employee.toEntity();
        body = employeeRepository.save(body);
        return body.toDTO();
    }

    @Override
    public EmployeeDTO update(Integer id, EmployeeDTO employee) {
        Employee body = employeeRepository.findById(id).get();
        body.setFirstName(employee.getFirstName());
        body.setLastName(employee.getLastName());
        body.setBirthDate(employee.getBirthDate());
        body.setGender(employee.getGender());
        body.setHireDate(employee.getHireDate());
        employeeRepository.save(body);
        return body.toDTO();
    }

    @Override
    public void deleteById(Integer empNo) {
        employeeRepository.deleteById(empNo);
    }
}
