package findo.lab.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import findo.lab.data.entity.Department;

public interface DepartmentService {
    Page<Department> findAll(Pageable pageable);
    Optional<Department> findById(String deptNo);
    Department save(Department department);
    void deleteById(String deptNo);
}
