package com.findo.employee_crud.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Employee {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    private String name;
    private Date dob;
    private String address;
    private String department;
}
