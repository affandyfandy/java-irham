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
