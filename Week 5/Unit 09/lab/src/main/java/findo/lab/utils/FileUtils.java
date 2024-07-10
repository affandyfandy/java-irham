package findo.lab.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import findo.lab.model.Employee;

public class FileUtils {

    public static String cleanCsvField(String field) {
        if (field == null) {
            return null;
        }
        return field.trim().replace("\b", "");
    }

    public static String getCleanCsvValue(CSVRecord csvRecord, String header) {
        return cleanCsvField(csvRecord.get(header));
    }

    public static List<Employee> getEmployeeFromCSV(MultipartFile file) {
        List<Employee> employees = new ArrayList<>();
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader("ID", "Name", "DateOfBirth", "Address", "Department", "Salary")
                .setSkipHeaderRecord(true)
                .build();

            try (CSVParser csvParser = new CSVParser(reader, csvFormat)) {
                for (CSVRecord csvRecord : csvParser) {
                    Employee employee = new Employee();
                    
                    employee.setId(getCleanCsvValue(csvRecord, "ID"));
                    employee.setName(getCleanCsvValue(csvRecord, "Name"));
                    employee.setDob(new SimpleDateFormat("dd/MM/yyyy").parse(getCleanCsvValue(csvRecord, "DateOfBirth")));
                    employee.setAddress(getCleanCsvValue(csvRecord, "Address"));
                    employee.setDepartment(getCleanCsvValue(csvRecord, "Department"));
                    employee.setSalary(Integer.parseInt(getCleanCsvValue(csvRecord, "Salary")));
                    
                    employees.add(employee);
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing CSV File: " + e.getLocalizedMessage());
        }
        return employees;
    }
}
