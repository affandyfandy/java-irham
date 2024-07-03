package fpt.lab.config;

import org.springframework.context.annotation.Bean;

import fpt.lab.controller.EmployeeWork;
import fpt.lab.model.Employee;

public class AppConfig {
    
    @Bean
    public EmployeeWork employeeWork() {
        return new EmployeeWork();
    }

    @Bean
    public Employee employee() {
        return new Employee("Irham", employeeWork());
    }
}
