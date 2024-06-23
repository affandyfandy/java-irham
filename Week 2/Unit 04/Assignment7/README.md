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

