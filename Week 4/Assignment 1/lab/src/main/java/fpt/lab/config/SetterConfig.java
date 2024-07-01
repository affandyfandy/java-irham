package fpt.lab.config;

import org.springframework.context.annotation.Bean;

import fpt.lab.controller.EmployeeWork;
import fpt.lab.model.EmployeeSetter;

public class SetterConfig {
    
    @Bean
    public EmployeeWork employeeWork() {
        return new EmployeeWork();
    }

    @Bean
    public EmployeeSetter employee() {
        EmployeeSetter employee = new EmployeeSetter();
        System.out.println("WITH SETTER");
        employee.setName("Irham");
        employee.setEmployeeWork(employeeWork());
        return employee;
    }
}
