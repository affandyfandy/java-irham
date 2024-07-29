package fpt.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fpt.lab.service.EmailService;

@Controller
@RequestMapping("/sample-request")
public class SampleController {
    
    private final EmailService emailService;

    public SampleController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    @ResponseBody
    public String handleRequest() {
        emailService.sendEmail("request@sample.com", "Request Scope Sample", "This is a request scoped sample email.");
        return "Request scope example";
    }
}
