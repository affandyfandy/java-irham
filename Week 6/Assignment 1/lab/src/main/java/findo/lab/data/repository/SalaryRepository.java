package findo.lab.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import findo.lab.data.entity.Salary;
import findo.lab.data.entity.SalaryId;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {
}
