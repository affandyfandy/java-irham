package com.assignment7.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.assignment7.models.Employee;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class FileUtils {

    // Read CSV using OpenCSV Library
    public static List<Employee> readCSVOpenCSV(String csvFile) {
        List<Employee> employees = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                Employee employee = Employee.fromCSV(line);
                employees.add(employee);
            }
        } catch (IOException | CsvValidationException error) {
            System.out.println("Can't Read CSV File: " + error.getLocalizedMessage());
        }
        return employees;
    }

    // Read CSV Manual
    public static List<Employee> readCSVManual(String csvFile) {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                Employee employee = Employee.fromCSV(fields);
                employees.add(employee);
            }

        } catch (IOException error) {
            System.out.println("Can't Read CSV File: " + error.getLocalizedMessage());
        }

        return employees;
    }

    // Write CSV
    public static void writeCSV(List<Employee> employees, String outputFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(outputFile))) {
            for (Employee employee : employees) {
                String[] line = { 
                    employee.getId(), 
                    employee.getName(), 
                    DateUtils.formatDate(employee.getDob()),
                    employee.getAddress(),
                    employee.getDepartment()
                };

                writer.writeNext(line);
            }
        } catch (IOException error) {
            System.out.println("Can't Export CSV File: " + error.getLocalizedMessage());
        }
    }
}
