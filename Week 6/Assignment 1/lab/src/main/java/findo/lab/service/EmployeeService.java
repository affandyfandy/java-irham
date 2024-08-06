package findo.lab.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import findo.lab.data.criteria.EmployeeSearchCriteria;
import findo.lab.dto.EmployeeDTO;

public interface EmployeeService {
    Page<EmployeeDTO> findAll(Pageable pageable);
    Page<EmployeeDTO> searchEmployees(EmployeeSearchCriteria criteria, Pageable pageable);
    Optional<EmployeeDTO> findById(Integer empNo);
    EmployeeDTO save(EmployeeDTO employee);
    EmployeeDTO update(Integer id, EmployeeDTO employee);
    void deleteById(Integer empNo);
}
