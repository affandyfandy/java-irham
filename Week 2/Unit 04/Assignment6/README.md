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
**[RemoveDuplicateCSV.java](RemoveDuplicateCSV.java)
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

