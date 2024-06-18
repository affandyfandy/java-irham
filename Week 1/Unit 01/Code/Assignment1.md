## Value Types
Value types are copied when passed as parameters, so modifications inside the function do not affect the original variable. This type contains primitive data type, such as: `int`, `float`, `double`, `char`, `boolean`.

**Example**:

```java
public class ValueTypeExample {
    public static void main(String[] args) {
        int a = 5; // Value type variable
        int b = a;  // Copying the value
        b = 10;     // Modifying the copied value
        System.out.println("a: " + a); // Output: 5
        System.out.println("b: " + b); // Output: 10
    }
}
```

When passing a primitive type to a function, Java passes a copy of the value, meaning the original value remains unchanged outside the function.

**Example**
```java
public class ValueTypeExample {

    public static void main(String[] args) {
        int originalValue = 10;
        System.out.println("Before: " + originalValue); // Output: Before: 10

        modifyValue(originalValue);
        System.out.println("After: " + originalValue); // Output: After: 10
    }

    public static void modifyValue(int value) {
        value = 20;
        System.out.println("Inside function: " + value); // Output: Inside function: 20
    }
}
```
In this example, `modifyValue` changes the local copy of the value parameter, but originalValue in main remains unchanged.

## Reference Types
Reference types are passed by reference, so modifications inside the function will affect the original object. Reference type such as: object of `array`, `String` and `custom classes`.

**Example**:

```java
public static void main(String[] args) {
    int[] array1 = {1, 2, 3}; // Reference type variable
    int[] array2 = array1;    // Copying the reference
    array2[0] = 10;           // Modifying the original array
    System.out.println("array1: " + Arrays.toString(array1)); // Output: [10, 2, 3]
    System.out.println("array2: " + Arrays.toString(array2)); // Output: [10, 2, 3]
}
```

When passing a reference type to a function, Java passes the reference to the object, meaning the function can modify the object’s state.

```java
class Person {
    String name;

    Person(String name) {
        this.name = name;
    }
}

public class ReferenceTypeExample {

    public static void main(String[] args) {
        Person person = new Person("John");
        System.out.println("Before: " + person.name); // Output: Before: John

        modifyPerson(person);
        System.out.println("After: " + person.name); // Output: After: Jane
    }

    public static void modifyPerson(Person p) {
        p.name = "Jane";
        System.out.println("Inside function: " + p.name); // Output: Inside function: Jane
    }
}
```

In this example, `modifyPerson` changes the name attribute of the person object, and this change is reflected outside the function because person is a reference to the original object.

## Value Types vs Reference Types
| Feature              | Value Types                     | Reference Types                   |
|----------------------|----------------------------------|-----------------------------------|
| **Storage Location** | Stack                            | Heap                              |
| **Contain Data**     | Directly                         | Reference to data                 |
| **Typical Usage**    | Primitive data, simple structures| Complex objects, collections      |
| **Passing Parameters** | By value (copy of data)         | By reference (reference to data)  |
| **Initialization**   | Direct assignment                | Using constructors                |
| **Performance**      | Faster (stack allocation)        | Slower (heap allocation)          |