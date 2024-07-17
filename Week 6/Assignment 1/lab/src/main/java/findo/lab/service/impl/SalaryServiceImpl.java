package findo.lab.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import findo.lab.data.entity.Salary;
import findo.lab.data.entity.Salary.SalaryId;
import findo.lab.data.repository.SalaryRepository;
import findo.lab.service.SalaryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SalaryServiceImpl implements SalaryService {
    
    private final SalaryRepository salaryRepository;

    @Override
    public Optional<Salary> findById(SalaryId id) {
        return salaryRepository.findById(id);
    }

    @Override
    public Salary save(Salary salary) {
        return salaryRepository.save(salary);
    }

    @Override
    public void deleteById(SalaryId id) {
        salaryRepository.deleteById(id);
    }
}
