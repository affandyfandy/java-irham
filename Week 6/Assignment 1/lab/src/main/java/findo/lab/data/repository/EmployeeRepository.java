package findo.lab.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import findo.lab.data.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
