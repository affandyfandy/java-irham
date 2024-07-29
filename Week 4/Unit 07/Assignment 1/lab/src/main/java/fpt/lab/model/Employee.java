package fpt.lab.model;

import fpt.lab.controller.EmployeeWork;

public class Employee {
    private String name;
    private EmployeeWork employeeWork;

    public Employee(String name, EmployeeWork employeeWork) {
        this.name = name;
        this.employeeWork = employeeWork;
    }

    public void working() {
        System.out.println("My name is " + name);
        employeeWork.work();
    }
}
