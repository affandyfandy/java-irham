package findo.lab.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import findo.lab.data.entity.DeptEmp;
import findo.lab.data.entity.DeptEmpId;

public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmpId> {
}
