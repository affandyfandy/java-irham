package fpt.lab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class FieldEmployeeService {
    
    @Autowired
    private EmailService emailService;

    public void notifyEmployee(String employeeEmail) {
        System.out.println("SEND WITH FIELD INJECTION");
        emailService.sendEmail(
            employeeEmail,
            "Daily Huddle", 
            "We invite you to join Product Daily Huddle");
    }
}
