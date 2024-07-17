package findo.lab.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import findo.lab.data.entity.Department;
import findo.lab.service.DepartmentService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/departments")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<Page<Department>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Department> departments = departmentService.findAll(pageable);

        if (departments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(departments);
    }

    @GetMapping(value = "/{deptNo}")
    public ResponseEntity<Department> findDepartmentById(@PathVariable("deptNo") String deptNo) {
        Optional<Department> departmentOpt= departmentService.findById(deptNo);

        if(departmentOpt.isPresent()) {
            return ResponseEntity.ok(departmentOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Department> save(@RequestBody Department department) {
        Optional<Department> departmentOpt = departmentService.findById(department.getDeptNo());
        
        if (departmentOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(departmentService.save(department));
    }

    @PutMapping(value = "/{deptNo}")
    public ResponseEntity<Department> update(@PathVariable("deptNo") String deptNo, @RequestBody Department department) {
        Optional<Department> departmentOpt = departmentService.findById(deptNo);
        
        if (departmentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(departmentService.save(department));
    }

    @DeleteMapping(value = "/{deptNo}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable(value = "deptNo") String deptNo) {
        Optional<Department> departmentOpt = departmentService.findById(deptNo);

        if(departmentOpt.isPresent()) {
            departmentService.deleteById(deptNo);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
