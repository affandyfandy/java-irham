package findo.lab.data.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {

	@Id
    @GeneratedValue(strategy=GenerationType.UUID)
	private String id;
    private String name;
    private Date dob;
    private String address;
    private String department;
    private String email;
    private String phone;
}
