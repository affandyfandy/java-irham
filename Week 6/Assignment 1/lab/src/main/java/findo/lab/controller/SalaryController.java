package findo.lab.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import findo.lab.data.entity.Salary;
import findo.lab.data.entity.Salary.SalaryId;
import findo.lab.service.SalaryService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/salaries")
@AllArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping
    public ResponseEntity<Salary> findSalaryById(@RequestBody SalaryId id) {
        Optional<Salary> salaryOpt= salaryService.findById(id);

        if(salaryOpt.isPresent()) {
            return ResponseEntity.ok(salaryOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Salary> save(@RequestBody Salary salary) {
        return ResponseEntity.ok(salaryService.save(salary));
    }

    @PutMapping
    public ResponseEntity<Salary> update(@RequestBody Salary salary) {
        Optional<Salary> salaryOpt = salaryService.findById(salary.getId());
        
        if (salaryOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(salaryService.save(salary));
    }

    @DeleteMapping
    public ResponseEntity<Salary> deleteSalary(@RequestBody SalaryId id) {
        Optional<Salary> salaryOpt = salaryService.findById(id);

        if(salaryOpt.isPresent()) {
            salaryService.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
