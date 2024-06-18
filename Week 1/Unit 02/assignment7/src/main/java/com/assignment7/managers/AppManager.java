package com.assignment7.managers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.assignment7.models.Employee;
import com.assignment7.utils.DateUtils;
import com.assignment7.utils.FileUtils;

public enum AppManager {
    INSTANCE;

    private List<Employee> employees;
    private final Scanner scanner = new Scanner(System.in);
    private boolean isUsingOpenCSV;

    public void start() {
        printHeader();
        while (true) { 
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 0 -> {
                    System.exit(0);
                }
                case 1 -> importData();
                case 2 -> addEmployee();
                case 3 -> filterEmployee(result->{});
                case 4 -> exportFilteredEmployees();
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void printHeader() {
        System.out.println("=== Employee Management System ===");
    }

    private void showMenu() {
        System.out.println("1 - Select File");
        System.out.println("2 - Add New Employee");
        System.out.println("3 - Filter by");
        System.out.println("4 - Filter & Export");
        System.out.println("0 - Exit");
        System.out.print("Choose an option: ");
    }

    private void importData() {
        System.out.println("1 - Open using OpenCSV");
        System.out.println("2 - Open Manual");
        System.out.print("Chose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());
        isUsingOpenCSV = (choice == 1);

        System.out.print("Enter file path: ");
        String fileName = scanner.nextLine();

        if (isUsingOpenCSV) {
            employees = FileUtils.readCSVOpenCSV(fileName);
        } else {
            employees = FileUtils.readCSVManual(fileName);
        }
    }

    private void addEmployee() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Date of Birth (d/M/yyyy): ");
        LocalDate dob = DateUtils.parsDate(scanner.nextLine());

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        employees.add(new Employee(id, name, dob, address, department));
    }

    private void filterEmployee(Consumer<List<Employee>> handler) {
        System.out.println("Filter by:");
        System.out.println("1 - ID");
        System.out.println("2 - Name");
        System.out.println("3 - Date of Birth");
        System.out.println("4 - Department");
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        List<Employee> filtered = new ArrayList<>();
        switch (choice) {
            case 1 -> {
                System.out.print("Enter ID: ");
                String id = scanner.nextLine();
                filtered = employees.stream().filter(item ->
                    item.getId().contains(id)
                ).collect(Collectors.toList());
            }
            case 2 -> {
                System.out.print("Enter Name: ");
                String name = scanner.nextLine();
                filtered = employees.stream().filter(item ->
                    item.getName().contains(name)
                ).collect(Collectors.toList());
            }
            case 3 -> {
                System.out.print("Enter Year of Birth: ");
                int year = Integer.parseInt(scanner.nextLine());
                filtered = employees.stream().filter(item ->
                    item.getDob().getYear() == year
                ).collect(Collectors.toList());
            }
            case 4 -> {
                System.out.print("Enter Department: ");
                String department = scanner.nextLine();
                filtered = employees.stream().filter(item ->
                    item.getDepartment().contains(department)
                ).collect(Collectors.toList());
            }
            default -> System.out.println("Invalid choice! Please try again.");
        }

        filtered.forEach(System.out::println);
        handler.accept(filtered);
    }

    private void exportFilteredEmployees() {
        filterEmployee(result -> {
            System.out.print("Enter output file name: ");
            String fileName = scanner.nextLine();

            FileUtils.writeCSV(result, fileName);
        });
    }
}
