package findo.lab.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import findo.lab.data.entity.DeptManager;
import findo.lab.data.entity.DeptManagerId;

public interface DeptManagerRepository extends JpaRepository<DeptManager, DeptManagerId> {
}
