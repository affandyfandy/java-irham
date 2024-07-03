package fpt.lab.model;

import fpt.lab.controller.EmployeeWork;

public class EmployeeSetter {
    private String name;
    private EmployeeWork employeeWork;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployeeWork(EmployeeWork employeeWork) {
        this.employeeWork = employeeWork;
    }

    // Method
    public void working() {
        System.out.println("My Name is: " + name);
        employeeWork.work();
    }
}
