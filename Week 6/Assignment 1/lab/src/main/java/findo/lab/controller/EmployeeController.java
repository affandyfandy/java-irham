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

import findo.lab.data.entity.Employee;
import findo.lab.service.EmployeeService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Page<Employee>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = employeeService.findAll(pageable);

        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(employees);
    }

    @GetMapping(value = "/{empNo}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable("empNo") Integer empNo) {
        Optional<Employee> employeeOpt= employeeService.findById(empNo);

        if(employeeOpt.isPresent()) {
            return ResponseEntity.ok(employeeOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Employee> save(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.save(employee));
    }

    @PutMapping(value = "/{empNo}")
    public ResponseEntity<Employee> update(@PathVariable Integer empNo, @RequestBody Employee employee) {
        Optional<Employee> employeeOpt = employeeService.findById(empNo);
        
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        employee.setEmpNo(empNo);
        return ResponseEntity.ok(employeeService.save(employee));
    }

    @DeleteMapping(value = "/{empNo}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable(value = "empNo") Integer empNo) {
        Optional<Employee> employeeOpt = employeeService.findById(empNo);

        if(employeeOpt.isPresent()) {
            employeeService.deleteById(empNo);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
