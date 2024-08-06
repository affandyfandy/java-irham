package findo.lab.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import findo.lab.data.entity.Salary;
import findo.lab.data.entity.SalaryId;
import findo.lab.data.repository.SalaryRepository;
import findo.lab.dto.SalaryDTO;
import findo.lab.service.SalaryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SalaryServiceImpl implements SalaryService {
    
    private final SalaryRepository salaryRepository;

    @Override
    public Optional<SalaryDTO> findById(SalaryId id) {
        return salaryRepository.findById(id).map(Salary::toDTO);
    }

    @Override
    public SalaryDTO save(SalaryDTO salary) {
        Salary body = salary.toEntity();
        body = salaryRepository.save(body);
        return body.toDTO();
    }

    @Override
    public SalaryDTO update(SalaryId id, SalaryDTO salary) {
        Salary body = salaryRepository.findById(id).get();
        body.setSalary(salary.getSalary());
        body.setToDate(salary.getToDate());
        body = salaryRepository.save(body);
        return body.toDTO();
    }

    @Override
    public void deleteById(SalaryId id) {
        salaryRepository.deleteById(id);
    }
}
