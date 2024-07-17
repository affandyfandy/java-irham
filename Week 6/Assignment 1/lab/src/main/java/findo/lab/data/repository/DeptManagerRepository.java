package findo.lab.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import findo.lab.data.entity.DeptManager;

public interface DeptManagerRepository extends JpaRepository<DeptManager, DeptManager.DeptManagerId> {
}
