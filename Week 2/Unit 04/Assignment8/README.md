# Assignment 8
This document contains several sub-task for Assignment 8

## `serialVersionUID` in Java

In Java, `serialVersionUID` is a unique identifier for serializable classes. When a Java object is serialized (converted into a byte stream to be stored or transmitted), `serialVersionUID` is used to verify that the sender and receiver of the serialized object have loaded classes for that object that are compatible with respect to serialization. If the sender and receiver have loaded classes for the object with different `serialVersionUID`, then deserialization will fail with an `InvalidClassException`.

### Why is serialVersionUID Important?

- **Version Control**: It helps in versioning of serialized objects. If the structure (fields, methods) of a class changes in a way that affects its serialized form, the `serialVersionUID` should be updated to indicate that the new version is different from the old one.

- **Compatibility**: Ensures that the serialized object can be deserialized properly even if the class definition has changed between the serialization and deserialization.

### How to Define serialVersionUID?

- `serialVersionUID` is a `private static final long` field in the class.
- If not explicitly defined, Java calculates it based on various aspects of the class, which can lead to different values if the class structure changes.
- It's recommended to define `serialVersionUID` explicitly to maintain control over serialization compatibility.

### Example of serialVersionUID Usage:

```java
import java.io.Serializable;

// Example class with serialVersionUID
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private transient double salary; // Example of transient field

    // Constructor, getters, setters, etc.
}
```

- `Employee` class implements `Serializable`.
- `serialVersionUID` is explicitly defined as `1L`.
- This ensures that serialized `Employee` objects will have a `serialVersionUID` of `1L`, regardless of changes to the class structure that do not affect serialization.

### Best Practices
- Always define serialVersionUID in classes that implement Serializable.
- Use a unique value (1L, 2L, etc.) and update it if the class structure changes in a way that affects serialization compatibility.


## Serialization and Deserialization in Java with serialVersionUID

Serialization in Java allows objects to be converted into a stream of bytes, which can then be stored persistently or transmitted over a network. Deserialization is the process of reconstructing objects from the serialized byte stream.

### Steps Involved:

1. **Implement Serializable Interface**: Ensure that the class (`Employee` in this case) implements the `Serializable` interface. This interface acts as a marker interface indicating that instances of the class can be serialized.

2. **Define serialVersionUID**: Define a `private static final long serialVersionUID` in the class to provide version control for serialized objects. This helps in ensuring that serialized objects can be deserialized properly even if the class definition changes.

3. **Serialization**:
   - Create an `ObjectOutputStream` to write objects to a file.
   - Use `writeObject()` method of `ObjectOutputStream` to serialize a list of `Employee` objects into a file.

4. **Deserialization**:
   - Create an `ObjectInputStream` to read objects from the file.
   - Use `readObject()` method of `ObjectInputStream` to deserialize the byte stream back into a list of `Employee` objects.

### Example Code:

```java
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Example class representing an Employee
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L; // serialVersionUID for versioning

    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    // Getters and setters (omitted for brevity)

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "John Doe", 5000));
        employees.add(new Employee(2, "Jane Smith", 6000));
        employees.add(new Employee(3, "Michael Johnson", 5500));

        // Serialization
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employees.ser"))) {
            oos.writeObject(employees);
            System.out.println("Serialization successful. Saved to employees.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialization
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("employees.ser"))) {
            List<Employee> restoredEmployees = (List<Employee>) ois.readObject();
            System.out.println("Deserialization successful. Retrieved from employees.ser");
            System.out.println("Restored Employees:");
            restoredEmployees.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```

#### Serialization:
- The `Employee` class implements `Serializable`.
- A list of `Employee` objects is serialized using `ObjectOutputStream` and saved to a file (`employees.ser`).
- `serialVersionUID` (set to `1L`) ensures version compatibility during deserialization.

#### Deserialization:
- The same `employees.ser` file is read using `ObjectInputStream`.
- The serialized byte stream is converted back into a list of `Employee` objects.
- Each `Employee` object's `toString()` method is called to print its details.

#### Output
```arduino
Serialization successful. Saved to employees.ser
Deserialization successful. Retrieved from employees.ser
Restored Employees:
Employee{id=1, name='John Doe', salary=5000.0}
Employee{id=2, name='Jane Smith', salary=6000.0}
Employee{id=3, name='Michael Johnson', salary=5500.0}
```