# Assignment 7
This document contains several sub-task for Assignment 7

## Remove duplicated items for any object and any duplicated fields​ using Java Generics
### [RemoveDuplicates.java](RemoveDuplicatesUtil.java)
```java
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
```

### Output
```arduino
Unique employees by name:
Employee{id=1, name='John Doe', age=30}
Employee{id=2, name='Jane Smith', age=28}
Employee{id=4, name='Michael Johnson', age=35}

Unique employees by age:
Employee{id=1, name='John Doe', age=30}
Employee{id=2, name='Jane Smith', age=28}
Employee{id=3, name='John Doe', age=32}
Employee{id=4, name='Michael Johnson', age=35}
Employee{id=5, name='Jane Smith', age=31}
```

## Demo: Using Wildcards With Generics
###[Box Class](DemoWildcards.java)
```java
class Box<T> {
    private T item;

    public Box(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Box{" +
                "item=" + item +
                '}';
    }
}
```

### [DemoWildcards Class](DemoWildcards.java)
```java
public class DemoWildcards {
    public static void main(String[] args) {
        // Box of integers
        Box<Integer> integerBox = new Box<>(10);
        printBox(integerBox);

        // Box of strings
        Box<String> stringBox = new Box<>("Hello, World!");
        printBox(stringBox);

        // List of boxes with wildcard
        List<Box<?>> boxes = new ArrayList<>();
        boxes.add(integerBox);
        boxes.add(stringBox);

        // Print contents of boxes
        System.out.println("Contents of boxes:");
        printBoxes(boxes);
    }

    // Method to print the contents of a specific box
    public static void printBox(Box<?> box) {
        System.out.println("Box contents: " + box.getItem());
    }

    // Method to print the contents of a list of boxes
    public static void printBoxes(List<Box<?>> boxes) {
        for (Box<?> box : boxes) {
            System.out.println(box);
        }
    }
}
```

## Output
```arduino
Box contents: 10
Box contents: Hello, World!
Contents of boxes:
Box{item=10}
Box{item=Hello, World!}
```

## Sort & Find Value using Generic
### [GenericOperations.java](GenericOperations.java)
```java
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
```

### Output
```arduino
Interns sorted by age:
Intern{id=2, name='Jane Smith', age=28, salary=6000.0}
Intern{id=1, name='John Doe', age=30, salary=5000.0}
Intern{id=5, name='David Williams', age=31, salary=5800.0}
Intern{id=4, name='Emily Brown', age=32, salary=5200.0}
Intern{id=3, name='Michael Johnson', age=35, salary=5500.0}

Intern with maximum salary: Intern{id=2, name='Jane Smith', age=28, salary=6000.0}
```