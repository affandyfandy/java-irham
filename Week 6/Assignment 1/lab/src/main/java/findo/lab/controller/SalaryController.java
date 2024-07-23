package findo.lab.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import findo.lab.data.entity.SalaryId;
import findo.lab.dto.SalaryDTO;
import findo.lab.service.SalaryService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/salaries")
@AllArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping
    public ResponseEntity<SalaryDTO> findSalaryById(@RequestBody SalaryId id) {
        Optional<SalaryDTO> salary = salaryService.findById(id);

        if (salary.isPresent()) {
            return ResponseEntity.ok(salaryService.findById(id).get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SalaryDTO> save(@RequestBody SalaryDTO salary) {
        return ResponseEntity.ok(salaryService.save(salary));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaryDTO> update(@PathVariable("id") SalaryId id, @RequestBody SalaryDTO salary) {
        return ResponseEntity.ok(salaryService.update(id, salary));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SalaryDTO> deleteSalary(@PathVariable("id") SalaryId id) {
        salaryService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
