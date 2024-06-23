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

