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

## Convert Generic List to Map
### [GenericListToMapConverter.java](GenericListToMapConverter.java)
```java
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
```

### Output
```arduino
Employee map by ID:
ID: 1, Employee: Employee{id=1, name='John Doe'}
ID: 2, Employee: Employee{id=2, name='Jane Smith'}
ID: 3, Employee: Employee{id=3, name='Michael Johnson'}
ID: 4, Employee: Employee{id=4, name='Emily Brown'}
ID: 5, Employee: Employee{id=5, name='David Williams'}

Employee map by Name:
Name: David Williams, Employee: Employee{id=5, name='David Williams'}
Name: John Doe, Employee: Employee{id=1, name='John Doe'}
Name: Michael Johnson, Employee: Employee{id=3, name='Michael Johnson'}
Name: Emily Brown, Employee: Employee{id=4, name='Emily Brown'}
Name: Jane Smith, Employee: Employee{id=2, name='Jane Smith'}
```

## Generic Class for Paging Data
### [Pager.java](Pager.java)
```java
public class Pager<T> {
    private List<T> data;
    private int pageSize;
    private int totalItems;
    private int currentPage;

    public Pager(List<T> data, int pageSize) {
        this.data = data;
        this.pageSize = pageSize;
        this.totalItems = data.size();
        this.currentPage = 1;
    }
    

    // Method to fetch and display current page of data
    public void showCurrentPage() {
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalItems);

        List<T> currentPageData = data.subList(startIndex, endIndex);
        System.out.println("Page " + currentPage + ": " + currentPageData);
    }

    // Method to navigate to next page
    public void nextPage() {
        if (currentPage < getTotalPages()) {
            currentPage++;
            showCurrentPage();
        } else {
            System.out.println("Already on the last page.");
        }
    }

    // Method to navigate to previous page
    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            showCurrentPage();
        } else {
            System.out.println("Already on the first page.");
        }
    }

    // Method to jump to a specific page number
    public void goToPage(int pageNumber) {
        if (pageNumber >= 1 && pageNumber <= getTotalPages()) {
            currentPage = pageNumber;
            showCurrentPage();
        } else {
            System.out.println("Invalid page number.");
        }
    }

    // Helper method to calculate total number of pages
    private int getTotalPages() {
        return (int) Math.ceil((double) totalItems / pageSize);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        this.totalItems = data.size();
        currentPage = 1; // Reset to first page when data changes
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }


    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
```

### Demo
```java
    public static void main(String[] args) {
        // Example usage with a list of Integer objects
        List<Integer> integerList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Pager<Integer> integerPager = new Pager<>(integerList, 3);

        // Display first page
        System.out.println("Initial page:");
        integerPager.showCurrentPage();

        // Navigate to next page
        System.out.println("\nNext page:");
        integerPager.nextPage();

        // Navigate to specific page
        System.out.println("\nGo to page 3:");
        integerPager.goToPage(3);

        // Navigate to previous page
        System.out.println("\nPrevious page:");
        integerPager.previousPage();

        // Change data and reset pager
        List<Integer> newIntegerList = List.of(11, 12, 13, 14, 15, 16);
        integerPager.setData(newIntegerList);
        integerPager.setPageSize(4);

        // Display first page of new data
        System.out.println("\nChanged data - Initial page:");
        integerPager.showCurrentPage();
    }
```

### Output
```arduino
Initial page:
Page 1: [1, 2, 3]

Next page:
Page 2: [4, 5, 6]

Go to page 3:
Page 3: [7, 8, 9]

Previous page:
Page 2: [4, 5, 6]

Changed data - Initial page:
Page 1: [11, 12, 13, 14]
```