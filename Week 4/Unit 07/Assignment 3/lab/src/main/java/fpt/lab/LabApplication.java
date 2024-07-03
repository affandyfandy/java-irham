package fpt.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fpt.lab.config.AppConfig;
import fpt.lab.service.ConsEmployeeService;
import fpt.lab.service.SetterEmployeeService;

@SpringBootApplication
public class LabApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabApplication.class, args);

		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Testing Constructor Injection
        ConsEmployeeService consEmployeeService1 = context.getBean(ConsEmployeeService.class);
        consEmployeeService1.notifyEmployee("irham1@mail.com");

        ConsEmployeeService consEmployeeService2 = context.getBean(ConsEmployeeService.class);
        System.out.println("ConsEmployeeService scope: " + (consEmployeeService1 == consEmployeeService2) + "\n");

        // Testing Setter Injection
        SetterEmployeeService setterEmployeeService1 = context.getBean(SetterEmployeeService.class);
        setterEmployeeService1.notifyEmployee("irham3@mail.com");

        SetterEmployeeService setterEmployeeService2 = context.getBean(SetterEmployeeService.class);
        System.out.println("SetterEmployeeService scope: " + (setterEmployeeService1 == setterEmployeeService2) + "\n");
	}
}
