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