package fpt.lab.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
