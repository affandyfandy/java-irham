# Assignment 3
This document contains several sub-task for Assignment 3
## Try-with-resources
The `try-with-resources` statement in Java is a feature introduced in Java 7 that simplifies the management of resources, such as streams, files, or database connections, which need to be closed after their operations are complete. It ensures that each resource is closed at the end of the statement, even if an exception occurs, thus preventing resource leaks.

### Syntax & Explanation
```java
try (ResourceType resource = new ResourceType()) {
    // Use the resource
} catch (ExceptionType e) {
    // Handle exceptions
} finally {
    // Optional: code to execute after the try block, regardless of exceptions
}
```

1. **Automatic Resource Management**: Resources are automatically closed when the try block is exited, whether it exits normally or due to an exception.
2. **Multiple Resources**: Multiple resources can be managed by a single try-with-resources statement by separating them with semicolons.
3. **Exception Handling**: Any exceptions thrown during the closing of resources are suppressed but can be retrieved and inspected if necessary.

**Example:**
```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TryWithResourcesExample {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("example.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
```
- `BufferedReader br = new BufferedReader(new FileReader("example.txt"))`: This statement initializes the BufferedReader resource within the `try` block.
- **Automatic Closing**: The `BufferedReader` is automatically closed at the end of the `try` block, even if an exception is thrown while reading the file.
- **Exception Handling**: If an `IOException` occurs, it is caught in the `catch` block and handled appropriately.

**Multiple Resources Example:**
```java
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TryWithMultipleResources {
    public static void main(String[] args) {
        try (FileInputStream in = new FileInputStream("input.txt");
             FileOutputStream out = new FileOutputStream("output.txt")) {

            int data;
            while ((data = in.read()) != -1) {
                out.write(data);
            }
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        }
    }
}
```
- **FileInputStream and FileOutputStream**: Two resources (**FileInputStream** and **FileOutputStream**) are declared and initialized in the try block.
- **Automatic Closing**: Both streams are automatically closed when the `try` block exits.
- **Exception Handling**: Any **IOException** thrown during the file operations or while closing the resources is caught and handled in the `catch` block.

### Benefits
- **Simplified Code**: Eliminates the need for explicit finally blocks to close resources.
- **Cleaner and Safer**: Ensures that resources are always properly closed, reducing the risk of resource leaks.
- **Enhanced Readability**: Makes the code more readable and easier to maintain.

## throw vs throws
### throw
`throw` is a keyword used to explicitly throw an exception within a method or a block of code. It is followed by an instance of an exception (usually created using the new keyword), which represents the occurrence of an exceptional condition during the execution of the program.

**Example:**
```java
public class ThrowExample {
    public static void main(String[] args) {
        int age = 17;
        try {
            if (age < 18) {
                throw new IllegalArgumentException("Age must be 18 or above");
            }
            System.out.println("Welcome! You are eligible.");
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}
```

- The `throw new IllegalArgumentException("Age must be 18 or above")` statement explicitly throws an `IllegalArgumentException` if the `age` is less than 18.
- The exception is caught in the `catch` block, and a custom message is printed.

### throws
`throws` is used in method declarations to indicate that a method might throw one or more types of exceptions. It specifies the types of exceptions that a method can throw, allowing callers of the method to handle or propagate those exceptions further.

**Example:**
```java
import java.io.FileReader;
import java.io.IOException;

public class ThrowsExample {
    public static void main(String[] args) {
        try {
            readFromFile("example.txt");
        } catch (IOException e) {
            System.out.println("IOException caught: " + e.getMessage());
        }
    }

    public static void readFromFile(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        // Read from file
        reader.close(); // If an exception occurs here, it will be propagated
    }
}
```

- The `readFromFile` method declares `throws IOException` in its signature, indicating that it may throw an `IOException`.
- In the `main` method, when calling `readFromFile("example.txt")`, the `IOException` is caught using a `catch` block.

### Summary
- **throw**: Used to throw an exception explicitly within a method or block.
- **throws**: Used in method declarations to specify the types of exceptions that a method can throw.

## Labs
- [Lab 1: try to use try-with-resources](Assignment3-Lab/src/Lab1.java)
- [Lab 2](Assignment3-Lab/src/Lab2.java)
- [Lab 3](Assignment3-Lab/src/Lab3.java)