# Assignment 6
This document contains several sub-task for Assignment 6

## Using Parallel Stream in Java

### When to Use Parallel Stream?

Parallel streams in Java are used to perform bulk operations on collections of data in parallel, which can enhance performance. Here are some scenarios where parallel streams can be particularly useful:

1. **Large Data Sets**: When dealing with large collections of data, parallel streams can significantly reduce processing time by utilizing multiple CPU cores.
2. **Independent Tasks**: When the tasks being performed on the elements are independent of each other, making it easy to parallelize the work.
3. **Complex Calculations**: When each element's processing is complex and takes a significant amount of time.

### Notice When Using Parallel Streams

1. **Thread Overhead**: Parallel streams use the ForkJoinPool common pool, which has a default size based on the number of available processors. Managing many threads can introduce overhead.
2. **Performance Gains**: These are not always guaranteed, especially for small collections or simple operations where the overhead of managing parallelism outweighs the benefits.
3. **Side Effects**: Operations should be stateless and should not cause side effects. Be cautious when using parallel streams with operations that modify shared state.

### Demo: Sum List of Numbers Using Parallel Stream

Below is a Java code example that demonstrates how to use a parallel stream to sum the elements of a list of numbers.

```java
import java.util.Arrays;
import java.util.List;

public class ParallelStreamDemo {
    public static void main(String[] args) {
        // Creating a list of numbers
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Summing the numbers using a parallel stream
        int sum = numbers.parallelStream()
                         .mapToInt(Integer::intValue)
                         .sum();
        
        // Printing the result
        System.out.println("Sum of numbers: " + sum);
    }
}
```

### Explanation
- **Creating a List**: A list of integers is created using `Arrays.asList`.
- **Parallel Stream**: The `parallelStream()` method is called on the list to create a parallel stream.
- **Mapping to Int**: The `mapToInt` method converts the Integer objects to primitive int values.
- **Summing**: The `sum()` method calculates the sum of the elements in the stream.
- **Printing**: The result is printed to the console.

## Remove all duplicate elements from a list of string using streams.​

**[RemoveDuplicates.java](RemoveDuplicates.java)**
```java
public class RemoveDuplicates {
    public static void main(String[] args) {
        // Creating a list of strings with duplicates
        List<String> strings = Arrays.asList("apple", "banana", "apple", "orange", "banana", "grape");
        
        // Removing duplicates using streams
        List<String> uniqueStrings = strings.stream()
                                            .distinct()
                                            .collect(Collectors.toList());
        
        // Printing the result
        System.out.println("List with duplicates removed: " + uniqueStrings);
    }
}
```
### Explanation
#### 1. Creating the List:
- We start with a list of `strings` containing duplicates such as `"apple"`, `"banana"`, `"apple"`, `"orange"`, `"banana"`, `"grape"`.

#### 2. Using Streams to Remove Duplicates:
- `strings.stream()`: Converts the list into a stream of elements.
- `.distinct()`: Applies a filter to keep only distinct elements in the stream.
- `.collect(Collectors.toList())`: Collects the elements of the stream into a new list (`uniqueStrings`) without duplicates.

#### 3. Printing the Result:
- Finally, we print the resulting list (`uniqueStrings`), which now contains only unique elements.

## Remove Duplicated Lines using Key Field
**[RemoveDuplicateCSV.java](RemoveDuplicateCSV.java)**
```java
public class RemoveDuplicateCSV {
    public static void main(String[] args) {
        String inputFilePath = "Week 2/Unit 04/Assignment6/data/fruits.csv";
        String outputFilePath = "Week 2/Unit 04/Assignment6/data/modified.csv";
        String keyFieldName = "id";

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            if (lines.isEmpty()) return;

            String header = lines.get(0);
            List<String> headers = Arrays.asList(header.split(","));
            int keyIndex = headers.indexOf(keyFieldName);
            if (keyIndex == -1) throw new IllegalArgumentException("Invalid key field name");

            List<String> uniqueLines = lines.stream()
                                            .skip(1) // Skip header
                                            .collect(Collectors.toMap(
                                                line -> line.split(",")[keyIndex],
                                                line -> line,
                                                (existing, replacement) -> existing
                                            ))
                                            .values()
                                            .stream()
                                            .collect(Collectors.toList());


            uniqueLines.add(0, header);

            Files.write(Paths.get(outputFilePath), uniqueLines);
        } catch (IOException e) {
            System.out.println("I/O Error occured:" + e);
        }
    }
}
```

## Count the number of strings in a list that start with a specific letter using streams.​
**[CountStrings.java](CountStrings.java)**
```java
public class CountStrings {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("Red", "Green", "Blue", "Pink", "Brown");
        String startingLetter = "G";

        // Using streams to count strings starting with the specific letter
        long count = strings.stream()
                            .filter(str -> str.startsWith(startingLetter))
                            .count();

        System.out.println("Number of strings starting with '" + startingLetter + "': " + count);
    }
}
```

## Sort & Find Data using Stream
### [EmployeeOperations.java](EmployeeOperations.java)
```java
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
```

### Output
```arduino
Employees sorted by name:
Employee{id=5, name='David Williams', age=31, salary=5800.0}
Employee{id=4, name='Emily Brown', age=32, salary=5200.0}
Employee{id=2, name='Jane Smith', age=28, salary=6000.0}
Employee{id=1, name='John Doe', age=30, salary=5000.0}
Employee{id=3, name='Michael Johnson', age=35, salary=5500.0}

Employee with maximum salary:
Employee{id=2, name='Jane Smith', age=28, salary=6000.0}

Any employee names match with keywords: true
```

## Convert list employees to map with ID as key Using Java Streams
### [ListToMap.java](ListToMap.java)
```java
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
```
### Output
```arduino
ID: 1, Employee: Employee{id=1, name='John Doe', age=30, salary=5000.0}
ID: 2, Employee: Employee{id=2, name='Jane Smith', age=28, salary=6000.0}
ID: 3, Employee: Employee{id=3, name='Michael Johnson', age=35, salary=5500.0}
ID: 4, Employee: Employee{id=4, name='Emily Brown', age=32, salary=5200.0}
ID: 5, Employee: Employee{id=5, name='David Williams', age=31, salary=5800.0}
```