import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class EmployeeOperations {
    public static void main(String[] args) {
        // Initialize list of employees
        List<Employee> employees = List.of(
            new Employee(1, "John Doe", 30, 5000),
            new Employee(2, "Jane Smith", 28, 6000),
            new Employee(3, "Michael Johnson", 35, 5500),
            new Employee(4, "Emily Brown", 32, 5200),
            new Employee(5, "David Williams", 31, 5800)
        );

        // Sort employees by name in alphabetical order (ascending)
        List<Employee> sortedByName = employees.stream()
                                              .sorted(Comparator.comparing(Employee::getName))
                                              .toList(); // Requires Java 16+

        System.out.println("Employees sorted by name:");
        sortedByName.forEach(System.out::println);
        System.out.println();

        // Find employee with maximum salary
        Optional<Employee> employeeWithMaxSalary = employees.stream()
                                                            .max(Comparator.comparingDouble(Employee::getSalary));

        employeeWithMaxSalary.ifPresent(emp -> {
            System.out.println("Employee with maximum salary:");
            System.out.println(emp);
        });
        System.out.println();

        // Check if any employee names match specific keywords
        String[] keywords = {"John", "Jane"};

        boolean anyMatch = employees.stream()
                                    .map(Employee::getName)
                                    .anyMatch(name -> {
                                        for (String keyword : keywords) {
                                            if (name.contains(keyword)) {
                                                return true;
                                            }
                                        }
                                        return false;
                                    });

        System.out.println("Any employee names match with keywords: " + anyMatch);
    }
}

class Employee {
    private int id;
    private String name;
    private int age;
    private double salary;

    public Employee(int id, String name, int age, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
