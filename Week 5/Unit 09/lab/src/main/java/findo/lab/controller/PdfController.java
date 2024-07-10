package findo.lab.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import findo.lab.model.Employee;
import findo.lab.service.EmployeeService;
import findo.lab.service.PdfGeneratorService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PdfController {

    private final EmployeeService employeeService;
    private final PdfGeneratorService pdfGeneratorService;

    @GetMapping("/employees/generate-pdf")
    public ResponseEntity<InputStreamResource> generatePdf() throws IOException {
        List<Employee> employees = employeeService.findAll();
        
        Employee highestSalaryEmployee = employees.stream().max(Comparator.comparing(Employee::getSalary)).orElse(null);
        Employee lowestSalaryEmployee = employees.stream().min(Comparator.comparing(Employee::getSalary)).orElse(null);
        int totalRecords = employees.size();
        double averageSalary = employees.stream().mapToDouble(Employee::getSalary).average().orElse(0);

        Map<String, Object> data = new HashMap<>();
        data.put("employees", employees);
        data.put("highestSalaryEmployee", highestSalaryEmployee);
        data.put("lowestSalaryEmployee", lowestSalaryEmployee);
        data.put("totalRecords", totalRecords);
        data.put("averageSalary", averageSalary);

        ByteArrayInputStream pdfStream = pdfGeneratorService.generatePdf("pdf-template", data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=employee-list.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }

}