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

import findo.lab.data.criteria.EmployeeSearchCriteria;
import findo.lab.dto.EmployeeDTO;
import findo.lab.service.EmployeeService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Optional<EmployeeSearchCriteria> criteria) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeDTO> employees = criteria.isEmpty() ? 
                employeeService.findAll(pageable) : 
                employeeService.searchEmployees(criteria.get(), pageable);

        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(employees);
    }

    @GetMapping(value = "/{empNo}")
    public ResponseEntity<EmployeeDTO> findEmployeeById(@PathVariable("empNo") Integer empNo) {
        Optional<EmployeeDTO> employeeOpt= employeeService.findById(empNo);

        if(employeeOpt.isPresent()) {
            return ResponseEntity.ok(employeeOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> save(@RequestBody EmployeeDTO employee) {
        return ResponseEntity.ok(employeeService.save(employee));
    }

    @PutMapping(value = "/{empNo}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Integer empNo, @RequestBody EmployeeDTO employee) {
        return ResponseEntity.ok(employeeService.update(empNo, employee));
    }

    @DeleteMapping(value = "/{empNo}")
    public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable(value = "empNo") Integer empNo) {
        employeeService.deleteById(empNo);
        return ResponseEntity.notFound().build();
    }
}
