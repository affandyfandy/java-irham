package fpt.lab.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fpt.lab.model.Employee;

@Repository
public class EmployeeRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Employee employee) {
        final String sql = "INSERT INTO Employee (id, name, dob, address, department) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            employee.getId(), 
            employee.getName(), 
            employee.getDob(), 
            employee.getAddress(), 
            employee.getDepartment()
        );
    }

    public int update(Employee employee) {
        final String sql = "UPDATE Employee SET name = ?, dob = ?, address = ?, department = ? WHERE id = ?";
        return jdbcTemplate.update(
            sql,
            employee.getName(), 
            employee.getDob(), 
            employee.getAddress(), 
            employee.getDepartment(), 
            employee.getId()
        );
    }

    public Employee findById(String id) {
        final String sql = "SELECT * FROM Employee WHERE id = ?";
        return jdbcTemplate.queryForObject(
            sql,
            new BeanPropertyRowMapper<>(Employee.class), 
            id
        );
    }

    public int deleteById(String id) {
        final String sql = "DELETE FROM Employee WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Employee> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM Employee", 
            new BeanPropertyRowMapper<>(Employee.class)
        );
    }

    public List<Employee> search(String query) {
        String sql = "SELECT * FROM Employee WHERE name LIKE ? OR address LIKE ? OR department LIKE ?";
        String likeQuery = "%" + query + "%";
        return jdbcTemplate.query(
            sql, 
            new BeanPropertyRowMapper<>(Employee.class), 
            likeQuery, 
            likeQuery, 
            likeQuery
        );
    }
}
