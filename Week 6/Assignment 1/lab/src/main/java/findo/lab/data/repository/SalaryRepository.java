package findo.lab.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import findo.lab.data.entity.Salary;

public interface SalaryRepository extends JpaRepository<Salary, Salary.SalaryId> {
}
