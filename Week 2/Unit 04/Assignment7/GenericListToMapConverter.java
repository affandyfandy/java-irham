import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenericListToMapConverter {
    // Method to convert List<T> to Map<K, T> using a keyExtractor function
    public static <T, K> Map<K, T> listToMap(List<T> list, Function<? super T, ? extends K> keyExtractor) {
        return list.stream()
                   .collect(Collectors.toMap(keyExtractor, Function.identity()));
    }

    public static void main(String[] args) {
        // Example usage with a list of Employee objects
        List<Employee> employees = List.of(
            new Employee(1, "John Doe"),
            new Employee(2, "Jane Smith"),
            new Employee(3, "Michael Johnson"),
            new Employee(4, "Emily Brown"),
            new Employee(5, "David Williams")
        );

        // Convert list of employees to a map with ID as key
        Map<Integer, Employee> employeeMapById = listToMap(employees, Employee::getId);
        System.out.println("Employee map by ID:");
        employeeMapById.forEach((id, emp) -> System.out.println("ID: " + id + ", Employee: " + emp));

        System.out.println();

        // Convert list of employees to a map with name as key
        Map<String, Employee> employeeMapByName = listToMap(employees, Employee::getName);
        System.out.println("Employee map by Name:");
        employeeMapByName.forEach((name, emp) -> System.out.println("Name: " + name + ", Employee: " + emp));
    }
}

// Example class representing an Employee
class Employee {
    private int id;
    private String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
