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


