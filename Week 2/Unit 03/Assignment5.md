# Assignment 5
This document contains several sub-task for Assignment 5

## ArrayList vs LinkedList
| Feature                | ArrayList                                        | LinkedList                                        |
|------------------------|--------------------------------------------------|--------------------------------------------------|
| **Underlying Structure** | Dynamic array                                    | Doubly linked list                                |
| **Access Time**        | O(1) for random access                           | O(n) for random access                            |
| **Insertion Time**     | O(1) amortized for adding at the end, O(n) for inserting/deleting elsewhere | O(1) for inserting/deleting at the beginning or end, O(n) for inserting/deleting in the middle |
| **Memory Usage**       | Less memory overhead, stores elements in a single array | More memory overhead, each element is a node with references to the previous and next nodes |
| **Iteration Performance** | Faster iteration due to better cache locality    | Slower iteration due to poorer cache locality    |
| **Use Case Examples**  | Suitable for scenarios with frequent random access and additions at the end | Suitable for scenarios with frequent insertions/deletions at the beginning, end, or middle |
| **Iterator Removal**   | Less efficient for iterator removal              | More efficient for iterator removal              |
| **Search Performance** | O(n)                                             | O(n)                                             |
| **Adding/Removing Elements** | Inefficient at positions other than the end (due to shifting elements) | Efficient at both ends (no need to shift elements) |
| **Thread Safety**      | Not synchronized                                 | Not synchronized                                 |
| **Capacity Management** | Grows dynamically, but may require resizing which can be costly | No capacity management, nodes are added as needed |

### Use ArrayList when:
#### 1. Random Access
- You need fast access to elements via their index.
- Example: Frequent get operations to access elements by index.

#### 2. Frequent Additions to the End
- You frequently add elements to the end of the list and rarely remove or insert them elsewhere.
- Example: Building a list by appending elements.

#### 3. Memory Efficient
- You need a collection with lower memory overhead.
- Example: Managing a large list of objects where space efficiency is important.

### Use LinkedList when:
#### 1. Frequent Insertion/Deletion
- You need to frequently insert or delete elements from the beginning, end, or middle of the list.
- Example: Implementing a queue (with frequent additions/removals at both ends).

#### 2. Iterator Removal
- You frequently remove elements using an iterator.
- Example: Removing elements while iterating over the list.

## HashSet vs TreeSet vs LinkedHashSet
| Feature                | HashSet                          | TreeSet                          | LinkedHashSet                    |
|------------------------|----------------------------------|----------------------------------|----------------------------------|
| **Order**              | No guaranteed order              | Sorted according to natural order or comparator | Insertion order                  |
| **Null Element**       | Allows one null element          | Does not allow null elements     | Allows one null element          |
| **Performance**        | O(1) for add, remove, contains   | O(log n) for add, remove, contains | O(1) for add, remove, contains   |
| **Synchronized**       | Not synchronized                 | Not synchronized                 | Not synchronized                 |
| **Fail-Fast or Fail-Safe Iterators** | Fail-fast                          | Fail-fast                          | Fail-fast                          |

### When to Use?
#### HashSet
- Use HashSet when you need a collection with fast performance for basic operations (add, remove, contains) and you do not care about the order of elements.
- Example Use Case: Maintaining a set of unique elements where order does not matter, such as a set of unique user IDs.
#### TreeSet
- Use TreeSet when you need a sorted set with the ability to perform range operations (e.g., finding subsets).
- Example Use Case: Keeping a sorted set of items, like maintaining a sorted collection of names or integers where elements are frequently retrieved in sorted order.
#### LinkedHashSet
- Use LinkedHashSet when you need a collection with predictable iteration order, which is the order in which elements were inserted.
- Example Use Case: Maintaining a collection of elements where the order of insertion is important, such as preserving the order of tasks to be performed.


## Write a Java program to retrieve an element (at a specified index) from a given array list.
**[RetrieveElement.java](Assignment5-Lab/src/RetrieveElement.java)**
```java
import java.util.ArrayList;
import java.util.Scanner;

public class RetrieveElement {
    public static void main(String[] args) {
        // Create an ArrayList and add some elements
        ArrayList<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        list.add("Date");
        list.add("Elderberry");

        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("The elements in the ArrayList are: " + list);

        // Prompt the user to enter an index
        System.out.print("Enter the index of the element to retrieve: ");
        int index = scanner.nextInt();

        // Retrieve the element at the specified index
        try {
            String element = list.get(index);
            System.out.println("Element at index " + index + ": " + element);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index. Please enter a valid index between 0 and " + (list.size() - 1));
        }

        // Close the scanner
        scanner.close();
    }
}
```

## Remove lines which is duplicated data by 1 key field
**[RemoveDuplicate.java](Assignment5-Lab/src/RemoveDuplicate.java)**
```java
public class RemoveDuplicate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String basePath = "Week 2/Unit 03/Assignment5-Lab/";
        String delimiter = ","; // Parse CSV format
        // Set to store unique keys (id)
        Set<String> seenKeys = new HashSet<>();

        System.out.print("Input file name: ");
        String inputFileName = basePath + scanner.nextLine();

        System.out.print("Output file name: ");
        String outputFileName = basePath + scanner.nextLine();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) {
             
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into fields
                String[] fields = line.split(delimiter);
                
                // Ensure there are enough fields and key field is valid
                if (fields.length > 1) {
                    String key = fields[0]; // Assuming id is the first field
                    if (!seenKeys.contains(key)) {
                        seenKeys.add(key); // Add the key to set (marks as seen)
                        writer.println(line); // Write the line to output
                    }
                }
            }
            
            System.out.println("Duplicates removed successfully. Output written to " + outputFileName);
            
        } catch (IOException e) {
            System.out.println("I/O Error occured:" + e);
        }
    }
}
```

## Convert List to Map
**[ConvertListToMap.java](Assignment5-Lab/src/ConvertListToMap.java)**
```java
public class ConvertListToMap {
    
    public static void main(String[] args) {
        try {
            List<Map<String, String>> list = readCSV();
            Map<Integer, Map<String, String>> map = convertListToMap(list);
            boolean isAscending = getUserInput();
            Map<Integer, Map<String, String>> sortedMap = sortByKey(map, isAscending);
            printSortedMap(sortedMap);
        } catch (IOException e) {
            System.out.println("Error occured: " + e.getMessage());
        }
    }

    private static boolean getUserInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Select sorting order:");
                System.out.println("1. ASCENDING");
                System.out.println("2. DESCENDING");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> {
                        return true;
                    }
                    case 2 -> {
                        return false;
                    }
                    default -> System.out.println("Invalid input. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private static List<Map<String, String>> readCSV() throws IOException {
        String inputFile = "Week 2/Unit 03/Assignment5-Lab/modified.csv";
        List<Map<String, String>> list;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            list = new ArrayList<>();
            String headerLine = reader.readLine();
            String[] headers = headerLine.split(",");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    map.put(headers[i], values[i]);
                }
                list.add(map);
            }
        }
        return list;
    }

    private static Map<Integer, Map<String, String>> convertListToMap(List<Map<String, String>> list) {
        Map<Integer, Map<String, String>> map = new HashMap<>();
        for (Map<String, String> item : list) {
            int id = Integer.parseInt(item.get("id"));
            map.put(id, item);
        }
        return map;
    }

    private static Map<Integer, Map<String, String>> sortByKey(Map<Integer, Map<String, String>> map, boolean  isDescending) {
        Comparator<Map.Entry<Integer, Map<String, String>>> comparator = Map.Entry.comparingByKey();
        if (isDescending) {
            comparator = comparator.reversed();
        }

        return map.entrySet()
                  .stream()
                  .sorted(comparator)
                  .collect(Collectors.toMap(
                      Map.Entry::getKey,
                      Map.Entry::getValue,
                      (e1, e2) -> e1,
                      LinkedHashMap::new
                  ));
    }

    private static void printSortedMap(Map<Integer, Map<String, String>> sortedMap) {
        for (Map.Entry<Integer, Map<String, String>> entry : sortedMap.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
```

## `equal()` and `hashCode()` Methods in Java

In Java, the `equals()` and `hashCode()` methods are crucial for comparing objects and for using objects as keys in collections like `HashMap`, `HashSet`, etc.

## `equals()` Method

The `equals()` method is used to compare two objects for equality. The default implementation in the `Object` class checks for reference equality, i.e., whether the two references point to the same object. However, you can override this method to provide your own definition of object equality based on the object's state.

## `hashCode()` Method

The `hashCode()` method returns an integer representation of the object, which is used for hashing in data structures like `HashMap`, `HashSet`, etc. The general contract of `hashCode()` is:

1. **Consistent:** Multiple invocations of `hashCode()` on the same object should consistently return the same integer, provided no information used in `equals()` comparisons on the object is modified.
2. **Equal objects must have equal hash codes:** If two objects are equal according to the `equals(Object)` method, then calling the `hashCode()` method on each of the two objects must produce the same integer result.
3. **Unequal objects may have unequal hash codes:** It is not required for unequal objects to have distinct hash codes. However, producing distinct hash codes for unequal objects can improve the performance of hash tables.

## Example

Let's consider a class `Person` with `name` and `age` as attributes. We will override the `equals()` and `hashCode()` methods in this class.

```java
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + '}';
    }

    public static void main(String[] args) {
        Person person1 = new Person("Alice", 30);
        Person person2 = new Person("Alice", 30);
        Person person3 = new Person("Bob", 25);

        // Check equality
        System.out.println("person1 equals person2: " + person1.equals(person2)); // true
        System.out.println("person1 equals person3: " + person1.equals(person3)); // false

        // Check hash codes
        System.out.println("person1 hashCode: " + person1.hashCode());
        System.out.println("person2 hashCode: " + person2.hashCode());
        System.out.println("person3 hashCode: " + person3.hashCode());

        // Using Person objects in a HashSet
        Set<Person> people = new HashSet<>();
        people.add(person1);
        people.add(person2);
        people.add(person3);

        System.out.println("People set: " + people);
    }
}
```

### Explanation
#### 1. `equals()` Method:
- First, it checks if the objects are the same instance using this == o.
- Then, it checks if the other object is null or if they are from different classes.
- Finally, it compares the state (i.e., `name` and `age`) of the objects.
#### 2. `hashCode()` Method:
- Uses `Objects.hash()` to generate a hash code based on the `name` and `age`.
#### 3. Usage:
- The `equals()` method is used to compare two `Person` objects.
- The `hashCode()` method ensures that `Person` objects with the same `name` and `age` will have the same hash code.
- When adding `Person` objects to a `HashSet`, the `equals()` and `hashCode()` methods ensure that duplicate entries (objects with the same `name` and `age`) are not added.

### Summary
- The `equals()` method is used to define what it means for two objects to be equal.
- The `hashCode()` method is used in hashing data structures to efficiently locate objects.
- When you override `equals()`, you must also override `hashCode()` to maintain the contract between these methods.
- Proper implementation of these methods is crucial for the correct functioning of collections that rely on hashing.

## Remove Duplicated ID with HashMap
# Adding Fruits to HashSet and Recognizing Duplicates by Fruit ID

In this example, we will demonstrate how to add fruits to a `HashSet` in Java and ensure it recognizes when two fruits have the same fruit ID by overriding the `equals()` and `hashCode()` methods.

## Step-by-Step Implementation

1. **Create a `Fruit` class** with `id`, `name`, and `quantity` as attributes.
2. **Override `equals()` and `hashCode()` methods** in the `Fruit` class to ensure two `Fruit` objects are considered equal if they have the same `id`.
3. **Add fruits to a `HashSet`** and check for duplicates.

### `Fruit` Class with Overridden `equals()` and `hashCode()`

```java
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Fruit {
    private int id;
    private String name;
    private int quantity;

    public Fruit(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fruit fruit = (Fruit) o;
        return id == fruit.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Fruit{id=" + id + ", name='" + name + "', quantity=" + quantity + "}";
    }
}
```

### Adding Fruits to `HashSet` and Checking for Duplicates
```java
public class Main {
    public static void main(String[] args) {
        Set<Fruit> fruits = new HashSet<>();

        Fruit apple = new Fruit(1, "Apple", 20);
        Fruit banana = new Fruit(2, "Banana", 12);
        Fruit cherry = new Fruit(3, "Cherry", 44);
        Fruit duplicateApple = new Fruit(1, "Apple", 25); // Duplicate ID

        fruits.add(apple);
        fruits.add(banana);
        fruits.add(cherry);
        fruits.add(duplicateApple); // Attempt to add duplicate

        System.out.println("Fruits in the set:");
        for (Fruit fruit : fruits) {
            System.out.println(fruit);
        }
    }
}
```

### Explanation
#### 1. `equals()` Method:
- Compares the id of the Fruit objects to determine equality.
#### 2. `hashCode()` Method:
- Generates the hash code based on the `id` of the Fruit.
#### 3. Using HashSet:
- When adding fruits to the `HashSet`, it uses the `equals()` and `hashCode()` methods to check for duplicates.
- Since `duplicateApple` has the same `id` as apple, it will not be added to the `HashSet`.

### Output
```
Fruits in the set:
Fruit{id=1, name='Apple', quantity=20}
Fruit{id=2, name='Banana', quantity=12}
Fruit{id=3, name='Cherry', quantity=44}
```

## Creating a Map of Employees with a Composite Key (Department, EmployeeID)

In this example, we will demonstrate how to create a `Map` in Java where the key is a composite key consisting of `department` and `employeeID`, and the value is an `Employee` object.

### Step-by-Step Implementation

1. **Create an `Employee` class** with `department`, `employeeID`, `name`, and `position` as attributes.
2. **Create a `CompositeKey` class** to represent the composite key with `department` and `employeeID` as attributes.
3. **Override `equals()` and `hashCode()` methods** in the `CompositeKey` class to ensure proper key comparison.
4. **Use a `HashMap`** to store `Employee` objects with `CompositeKey` as the key.

### `Employee` Class

```java
public class Employee {
    private String department;
    private int employeeID;
    private String name;
    private String position;

    public Employee(String department, int employeeID, String name, String position) {
        this.department = department;
        this.employeeID = employeeID;
        this.name = name;
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "department='" + department + '\'' +
                ", employeeID=" + employeeID +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
```

### `CompositeKey` Class
```java
import java.util.Objects;

public class CompositeKey {
    private String department;
    private int employeeID;

    public CompositeKey(String department, int employeeID) {
        this.department = department;
        this.employeeID = employeeID;
    }

    public String getDepartment() {
        return department;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeKey that = (CompositeKey) o;
        return employeeID == that.employeeID &&
                Objects.equals(department, that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(department, employeeID);
    }

    @Override
    public String toString() {
        return "CompositeKey{" +
                "department='" + department + '\'' +
                ", employeeID=" + employeeID +
                '}';
    }
}
```

### Using `HashMap` with `CompositeKey`

```java
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<CompositeKey, Employee> employeeMap = new HashMap<>();

        Employee emp1 = new Employee("IT", 101, "Alice", "Developer");
        Employee emp2 = new Employee("HR", 102, "Bob", "Manager");
        Employee emp3 = new Employee("Finance", 103, "Charlie", "Analyst");
        Employee emp4 = new Employee("IT", 104, "David", "Tester");

        CompositeKey key1 = new CompositeKey("IT", 101);
        CompositeKey key2 = new CompositeKey("HR", 102);
        CompositeKey key3 = new CompositeKey("Finance", 103);
        CompositeKey key4 = new CompositeKey("IT", 104);

        employeeMap.put(key1, emp1);
        employeeMap.put(key2, emp2);
        employeeMap.put(key3, emp3);
        employeeMap.put(key4, emp4);

        for (Map.Entry<CompositeKey, Employee> entry : employeeMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " -> Value: " + entry.getValue());
        }
    }
}
```

### Explanation
#### 1. `Employee` Class:
- Defines the attributes of an employee, including `department`, `employeeID`, `name`, and `position`.
- The `toString()` method is overridden to provide a string representation of the `Employee` object.

#### 2. `CompositeKey` Class:
- Contains `department` and `employeeID` as attributes.
- The `equals()` method is overridden to compare `CompositeKey` objects based on department and `employeeID`.
- The `hashCode()` method is overridden to generate a hash code based on `department` and `employeeID`.
- The `toString()` method is overridden to provide a string representation of the `CompositeKey` object.

#### 4. Using `HashMap`:
- A `HashMap` is used to store `Employee` objects with `CompositeKey` as the `key`.
- Employees are added to the `HashMap` with unique composite keys.
- The entries in the `HashMap` are iterated and printed, showing the mapping of composite keys to employee objects.


### Output
```arduino
Key: CompositeKey{department='IT', employeeID=101} -> Value: Employee{department='IT', employeeID=101, name='Alice', position='Developer'}

Key: CompositeKey{department='HR', employeeID=102} -> Value: Employee{department='HR', employeeID=102, name='Bob', position='Manager'}

Key: CompositeKey{department='Finance', employeeID=103} -> Value: Employee{department='Finance', employeeID=103, name='Charlie', position='Analyst'}

Key: CompositeKey{department='IT', employeeID=104} -> Value: Employee{department='IT', employeeID=104, name='David', position='Tester'}
```