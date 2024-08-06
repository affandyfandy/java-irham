package findo.lab.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import findo.lab.dto.DepartmentDTO;

public interface DepartmentService {
    Page<DepartmentDTO> findAll(Pageable pageable);
    Optional<DepartmentDTO> findById(String deptNo);
    DepartmentDTO save(DepartmentDTO department);
    DepartmentDTO update(String id, DepartmentDTO department);
    void deleteById(String deptNo);
}
