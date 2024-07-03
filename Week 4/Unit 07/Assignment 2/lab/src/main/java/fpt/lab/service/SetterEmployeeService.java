package fpt.lab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetterEmployeeService {
    
    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void notifyEmployee(String employeeEmail) {
        System.out.println("SENDED WITH SETTER INJECTION");
        emailService.sendEmail(
            employeeEmail,
            "Daily Huddle", 
            "We invite you to join Product Daily Huddle");
    }
}
