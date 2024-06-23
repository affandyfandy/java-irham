import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListToMap {
    public static void main(String[] args) {
        // Example list of employees
        List<Employee> employees = List.of(
            new Employee(1, "John Doe", 30, 5000),
            new Employee(2, "Jane Smith", 28, 6000),
            new Employee(3, "Michael Johnson", 35, 5500),
            new Employee(4, "Emily Brown", 32, 5200),
            new Employee(5, "David Williams", 31, 5800)
        );

        // Convert List<Employee> to Map<Integer, Employee> using streams
        Map<Integer, Employee> employeeMap = employees.stream()
                                                      .collect(Collectors.toMap(Employee::getId, emp -> emp));

        // Print the resulting map
        employeeMap.forEach((id, emp) -> System.out.println("ID: " + id + ", Employee: " + emp));
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

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
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

