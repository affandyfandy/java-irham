package fpt.lab.config;

import org.springframework.context.annotation.Bean;

import fpt.lab.controller.EmployeeWork;
import fpt.lab.model.EmployeeField;

public class FieldConfig {
    
    @Bean
    public EmployeeWork employeeWork() {
        return new EmployeeWork();
    }

    @Bean
    public EmployeeField employee() {
        EmployeeField employee = new EmployeeField();
        employee.setName("Irham");
        return employee;
    }
}
