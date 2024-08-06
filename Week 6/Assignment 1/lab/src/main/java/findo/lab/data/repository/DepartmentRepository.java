package findo.lab.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import findo.lab.data.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
