package findo.lab.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import findo.lab.data.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:query% OR e.address LIKE %:query% OR e.department LIKE %:query% OR e.email LIKE %:query% OR e.phone LIKE %:query%")
    List<Employee> searchEmployees(@Param("query") String query);
}
