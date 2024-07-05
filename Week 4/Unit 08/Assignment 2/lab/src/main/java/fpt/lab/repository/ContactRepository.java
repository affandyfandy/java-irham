package fpt.lab.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fpt.lab.model.Contact;

@Repository
public class ContactRepository {

    @Autowired
    @Qualifier("contactJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public int save(Contact contact) {
        final String sql = "INSERT INTO contact (id, name, age, dob, email) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            contact.getId(), 
            contact.getName(), 
            contact.getAge(), 
            contact.getDob(), 
            contact.getEmail()
        );
    }

    public int update(Contact contact) {
        final String sql = "UPDATE contact SET name = ?, age = ?, dob = ?, email = ? WHERE id = ?";
        return jdbcTemplate.update(
            sql,
            contact.getName(), 
            contact.getAge(), 
            contact.getDob(), 
            contact.getEmail(), 
            contact.getId()
        );
    }

    public Contact findById(String id) {
        final String sql = "SELECT * FROM contact WHERE id = ?";
        return jdbcTemplate.queryForObject(
            sql,
            new BeanPropertyRowMapper<>(Contact.class), 
            id
        );
    }

    public int deleteById(String id) {
        final String sql = "DELETE FROM contact WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Contact> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM contact", 
            new BeanPropertyRowMapper<>(Contact.class)
        );
    }

    public List<Contact> search(String query) {
        String sql = "SELECT * FROM contact WHERE name LIKE ?";
        String likeQuery = "%" + query + "%";
        return jdbcTemplate.query(
            sql, 
            new BeanPropertyRowMapper<>(Contact.class), 
            likeQuery
        );
    }
}
