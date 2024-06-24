package com.findo.employee_crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.findo.employee_crud.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findByDepartment(String department);
}
