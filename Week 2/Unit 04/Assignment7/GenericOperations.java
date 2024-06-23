import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GenericOperations {
    // Method to sort a list by any field using a Comparator
    public static <T> void sortByField(List<T> list, Comparator<? super T> comparator) {
        List<T> mutableList = new ArrayList<>(list); // Convert to mutable list
        mutableList.sort(comparator);
        list.clear(); // Clear original list
        list.addAll(mutableList); // Copy sorted elements back to original list
    }

    // Method to find the item with the maximum value of any field using a Comparator
    public static <T> Optional<T> findMaxByField(List<T> list, Comparator<? super T> comparator) {
        return list.stream().max(comparator);
    }

    public static void main(String[] args) {
        // Example usage with a list of Employee objects
        List<Intern> interns = new ArrayList<>(List.of(
            new Intern(1, "John Doe", 30, 5000),
            new Intern(2, "Jane Smith", 28, 6000),
            new Intern(3, "Michael Johnson", 35, 5500),
            new Intern(4, "Emily Brown", 32, 5200),
            new Intern(5, "David Williams", 31, 5800)
        ));

        // Sort employees by age (ascending)
        sortByField(interns, Comparator.comparing(Intern::getAge));
        System.out.println("Interns sorted by age:");
        interns.forEach(System.out::println);
        System.out.println();

        // Find intern with maximum salary
        Optional<Intern> internWithMaxSalary = findMaxByField(interns, Comparator.comparing(Intern::getSalary));
        internWithMaxSalary.ifPresent(emp -> System.out.println("Intern with maximum salary: " + emp));
    }
}

// Example class representing an Intern
class Intern {
    private int id;
    private String name;
    private int age;
    private double salary;

    public Intern(int id, String name, int age, double salary) {
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
        return "Intern{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
