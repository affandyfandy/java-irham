package findo.lab.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import findo.lab.data.entity.Department;
import findo.lab.data.repository.DepartmentRepository;
import findo.lab.dto.DepartmentDTO;
import findo.lab.service.DepartmentService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    
    private final DepartmentRepository departmentRepository;

    @Override
    public Page<DepartmentDTO> findAll(Pageable pageable) {
        Page<Department> departments = departmentRepository.findAll(pageable);
        return departments.map(Department::toDTO);
    }

    @Override
    public Optional<DepartmentDTO> findById(String deptNo) {
        return departmentRepository.findById(deptNo).map(Department::toDTO);
    }

    @Override
    public DepartmentDTO save(DepartmentDTO department) {
        Department body = department.toEntity();
        body = departmentRepository.save(body);
        return body.toDTO();
    }

    @Override
    public DepartmentDTO update(String id, DepartmentDTO department) {
        Department body = departmentRepository.findById(id).get();
        body.setDeptName(body.getDeptName());
        body = departmentRepository.save(body);
        return body.toDTO();
    }

    @Override
    public void deleteById(String deptNo) {
        departmentRepository.deleteById(deptNo);
    }
}
