package fpt.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fpt.lab.config.AppConfig;
import fpt.lab.service.ConsEmployeeService;
import fpt.lab.service.FieldEmployeeService;
import fpt.lab.service.SetterEmployeeService;

@SpringBootApplication
public class LabApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabApplication.class, args);

		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Testing Constructor Injection
		ConsEmployeeService consEmployeeService = context.getBean(ConsEmployeeService.class);
		consEmployeeService.notifyEmployee("irham1@mail.com");

		// Testing Field Injection
		FieldEmployeeService fieldEmployeeService = context.getBean(FieldEmployeeService.class);
		fieldEmployeeService.notifyEmployee("irham2@mail.com");

		// Testing Setter Injection
		SetterEmployeeService setterEmployeeService = context.getBean(SetterEmployeeService.class);
		setterEmployeeService.notifyEmployee("irham3@mail.com");
	}

}
