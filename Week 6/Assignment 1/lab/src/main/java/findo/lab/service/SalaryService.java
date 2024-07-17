package findo.lab.service;

import java.util.Optional;

import findo.lab.data.entity.Salary;
import findo.lab.data.entity.Salary.SalaryId;

public interface SalaryService {
    Optional<Salary> findById(SalaryId id);
    Salary save(Salary salary);
    void deleteById(SalaryId id);
}
