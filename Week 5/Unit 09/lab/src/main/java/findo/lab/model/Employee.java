package findo.lab.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {

	@Id
	private String id;
    private String name;
    private Date dob;
    private String address;
    private String department;
    private int salary;
}