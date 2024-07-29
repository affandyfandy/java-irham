package fpt.lab.model;

import org.springframework.beans.factory.annotation.Autowired;

import fpt.lab.controller.EmployeeWork;

public class EmployeeField {
    private String name;

    @Autowired
    private EmployeeWork employeeWork;

    public void setName(String name) {
        this.name = name;
    }

    // Method
    public void working() {
        System.out.println("My Name is: " + name);
        employeeWork.work();
    }
}
