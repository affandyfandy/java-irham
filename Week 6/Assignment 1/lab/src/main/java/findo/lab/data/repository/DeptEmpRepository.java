package findo.lab.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import findo.lab.data.entity.DeptEmp;

public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmp.DeptEmpId> {
}
