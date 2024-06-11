## Value Types
Value types are copied when passed as parameters, so modifications inside the function do not affect the original variable.

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

## Reference Types
Reference types are passed by reference, so modifications inside the function will affect the original object.
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