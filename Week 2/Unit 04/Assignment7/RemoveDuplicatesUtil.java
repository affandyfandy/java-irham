import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class RemoveDuplicatesUtil {
    public static <T> List<T> removeDuplicates(List<T> list, Function<? super T, ?> keyExtractor) {
        Set<Object> seen = new HashSet<>();
        List<T> result = new ArrayList<>();
        
        for (T item : list) {
            Object key = keyExtractor.apply(item);
            if (seen.add(key)) {
                result.add(item);
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        // Example usage for a list of Employee objects
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "John Doe", 30));
        employees.add(new Employee(2, "Jane Smith", 28));
        employees.add(new Employee(3, "John Doe", 32));
        employees.add(new Employee(4, "Michael Johnson", 35));
        employees.add(new Employee(5, "Jane Smith", 31));
        
        // Remove duplicates based on name
        List<Employee> uniqueEmployeesByName = removeDuplicates(employees, Employee::getName);
        System.out.println("Unique employees by name:");
        uniqueEmployeesByName.forEach(System.out::println);
        
        System.out.println();
        
        // Remove duplicates based on age
        List<Employee> uniqueEmployeesByAge = removeDuplicates(employees, Employee::getAge);
        System.out.println("Unique employees by age:");
        uniqueEmployeesByAge.forEach(System.out::println);
    }
}

class Employee {
    private int id;
    private String name;
    private int age;

    public Employee(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
