package findo.lab.service;

import java.util.Optional;

import findo.lab.data.entity.SalaryId;
import findo.lab.dto.SalaryDTO;

public interface SalaryService {
    Optional<SalaryDTO> findById(SalaryId id);
    SalaryDTO save(SalaryDTO salary);
    SalaryDTO update(SalaryId id, SalaryDTO salary);
    void deleteById(SalaryId id);
}
