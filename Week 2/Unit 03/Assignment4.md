# Assignment 4
This document contains several sub-task for Assignment 4
## Deadlock in Java
A **deadlock** is a situation in a concurrent system where two or more threads are unable to proceed because each is waiting for the other to release a resource they need. In simpler terms, it's a standstill where threads are stuck waiting for each other indefinitely.

**Example:**
```java
public class DeadlockExample {
    public static void main(String[] args) {
        final Object resource1 = "resource1";
        final Object resource2 = "resource2";

        // Thread 1 tries to lock resource1 then resource2
        Thread t1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1: locked resource 1");

                try { Thread.sleep(100); } catch (Exception e) {}

                synchronized (resource2) {
                    System.out.println("Thread 1: locked resource 2");
                }
            }
        });

        // Thread 2 tries to lock resource2 then resource1
        Thread t2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread 2: locked resource 2");

                try { Thread.sleep(100); } catch (Exception e) {}

                synchronized (resource1) {
                    System.out.println("Thread 2: locked resource 1");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
```
- `Thread 1` locks `resource1` and then tries to lock `resource2`.
- `Thread 2` locks `resource2` and then tries to lock `resource1`.
- Both threads end up waiting for each other to release the locked resources, causing a deadlock.

### How to Prevent Deadlock?
1. **Avoid Nested Locks**: Try to avoid situations where multiple locks are held simultaneously.
2. **Lock Ordering**: Always acquire locks in the same order in all threads. If all threads lock resources in a consistent order, deadlocks can be avoided.
3. **Timeouts**: Use lock attempts with timeouts, such as tryLock(long timeout, TimeUnit unit). If the timeout expires, the thread can release any resources it holds and try again later.
4. **Deadlock Detection**: Implement deadlock detection algorithms that can identify and recover from deadlocks. This approach is complex and not commonly used in application-level code.
5. **Avoid Long-Running Locks**: Hold locks for the shortest duration possible. This reduces the chance of a thread holding a lock while waiting for another resource.


### Example of Preventing Deadlock with Lock Ordering
Here's a modified version of the previous example to prevent deadlock by ensuring that both threads acquire the locks in the same order:
```java
public class DeadlockPreventionExample {
    public static void main(String[] args) {
        final Object resource1 = "resource1";
        final Object resource2 = "resource2";

        // Thread 1 tries to lock resources in order: resource1, resource2
        Thread t1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1: locked resource 1");

                try { Thread.sleep(100); } catch (Exception e) {}

                synchronized (resource2) {
                    System.out.println("Thread 1: locked resource 2");
                }
            }
        });

        // Thread 2 also tries to lock resources in the same order: resource1, resource2
        Thread t2 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 2: locked resource 1");

                try { Thread.sleep(100); } catch (Exception e) {}

                synchronized (resource2) {
                    System.out.println("Thread 2: locked resource 2");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
```
By acquiring locks in a consistent order (resource1 followed by resource2), we prevent the circular wait condition, which is one of the necessary conditions for a deadlock to occur.

## Creates a bank account with concurrent deposits and withdrawals using threads.
**[BankAccount.java](Assignment4-Lab/src/BankAccount.java)**
```java
public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    // Synchronized method to deposit money
    public synchronized void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " deposited " + amount + ". New balance: " + balance);
        }
    }

    // Synchronized method to withdraw money
    public synchronized void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrew " + amount + ". New balance: " + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " failed to withdraw " + amount + ". Insufficient balance.");
        }
    }

    public double getBalance() {
        return balance;
    }
}
```

- This class has a `balance` field to store the account balance.
- The `deposit` and `withdraw` methods are synchronized to ensure thread safety during concurrent access.
- The `deposit` method adds the specified amount to the balance.
- The `withdraw` method subtracts the specified amount from the balance if there are sufficient funds.

<br>

**[DepositThread.java](Assignment4-Lab/src/DepositThread.java)**
```java
public class DepositThread extends Thread {
    private BankAccount account;
    private double amount;

    public DepositThread(BankAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.deposit(amount);
    }
}
```
This class extends `Thread` and performs a deposit operation on the `BankAccount` instance in its run method.

<br>

**[WithdrawThread.java](Assignment4-Lab/src/WithdrawThread.java)**
```java
public class WithdrawThread extends Thread {
    private BankAccount account;
    private double amount;

    public WithdrawThread(BankAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount);
    }
}
```
This class extends `Thread` and performs a withdrawal operation on the `BankAccount` instance in its run method.

<br>

**[BankApplication.java](Assignment4-Lab/src/BankApplication.java)**
```java
public class BankApplication {
    public static void main(String[] args) {
        // Create a bank account with an initial balance
        BankAccount account = new BankAccount(1000.00);

        // Create threads for concurrent deposits and withdrawals
        Thread t1 = new DepositThread(account, 200.00);
        Thread t2 = new DepositThread(account, 150.00);
        Thread t3 = new WithdrawThread(account, 100.00);
        Thread t4 = new WithdrawThread(account, 500.00);
        Thread t5 = new WithdrawThread(account, 300.00);

        // Start the threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        // Print final balance
        System.out.println("Final balance: " + account.getBalance());
    }
}
```

- This class contains the main method where the BankAccount is created and multiple DepositThread and WithdrawThread instances are started.
- It waits for all threads to finish using the join method to ensure all operations are completed before printing the final balance.

### Sample Output:
```
Thread-0 deposited 200.0. New balance: 1200.0
Thread-3 withdrew 500.0. New balance: 700.0
Thread-4 withdrew 300.0. New balance: 400.0
Thread-1 deposited 150.0. New balance: 550.0
Thread-2 withdrew 100.0. New balance: 450.0
Final balance: 450.0
```

<br>

## Write a Java program that sorts an array of integers using multiple threads.
**[ArraySort.java](Assignment4-Lab/src/ArraySort.java)**
```java
import java.util.Arrays;

public class ArraySort implements Runnable {
    private int[] array;
    private int left;
    private int right;

    public ArraySort(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        sort(array, left, right);
    }

    public void sort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;

            Thread leftSorter = new Thread(new ArraySort(array, left, middle));
            Thread rightSorter = new Thread(new ArraySort(array, middle + 1, right));

            leftSorter.start();
            rightSorter.start();

            try {
                leftSorter.join();
                rightSorter.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            merge(array, left, middle, right);
        }
    }

    public void merge(int[] array, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, middle + 1, rightArray, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        int[] array = {38, 27, 43, 3, 9, 82, 10};

        System.out.println("Original array: " + Arrays.toString(array));

        ArraySort sorter = new ArraySort(array, 0, array.length - 1);
        sorter.sort(array, 0, array.length - 1);

        System.out.println("Sorted array: " + Arrays.toString(array));
    }
}
```

- Implements `Runnable` to allow sorting tasks to be executed by threads.
- The constructor initializes the array segment to be sorted.
- The `run` method calls the `sort` method to perform the sorting.
- The `sort` method:
    - Divides the array into two halves.
    - Creates and starts two threads to sort each half concurrently.
    - Waits for both threads to finish using join.
    - Merges the sorted halves.
- The `merge` method combines two sorted subarrays into a single sorted array.

### Sample Output
```
Original array: [38, 27, 43, 3, 9, 82, 10]
Sorted array: [3, 9, 10, 27, 38, 43, 82]
```

<br>

## What are noticeable things when using multiple thread?
### 1. Thread Safety and Synchronization
- **Race Conditions**: When two or more threads access shared data and try to change it simultaneously, race conditions can occur. This can lead to inconsistent or incorrect data.
- **Synchronization**: Use synchronization mechanisms (like the `synchronized` keyword, locks, or concurrent data structures) to control access to shared resources and avoid race conditions.

### 2. Deadlocks
- **Definition**: A situation where two or more threads are blocked forever, waiting for each other.
- **Prevention**: Avoid nested locks, use lock ordering, and consider timeouts when acquiring locks.

### 3. Performance Overhead
- **Context Switching**: Switching between threads involves overhead. Excessive context switching can degrade performance.
- **Thread Creation**: Creating and destroying threads is costly in terms of performance. Use thread pools to manage and reuse threads.

### 4. Concurrency Issues
- **Atomicity**: Ensure that operations on shared variables are atomic. Use classes from `java.util.concurrent.atomic` package for atomic operations.
- **Visibility**: Changes made by one thread might not be visible to other threads. Use `volatile` keyword or synchronization to ensure visibility of changes.

### 5. Resource Management
- **Memory**: Threads consume memory. Too many threads can lead to `OutOfMemoryError`.
- **Resource Locking**: Properly manage locks and other resources to avoid resource leaks.

### 6. Thread Coordiation
- **Wait/Notify**: Use wait(), notify(), and notifyAll() methods for coordinating threads.
- CountDownLatch, CyclicBarrier, Phaser: Higher-level synchronization utilities for coordinating multiple threads.

### 7. Thread Lifecycle
- **States**: Understand thread states (New, Runnable, Blocked, Waiting, Timed Waiting, Terminated) to manage thread lifecycle properly.
- **Daemon Threads**: Use daemon threads for background tasks that should not prevent the JVM from exiting.

### 8. Exceptions Handling
**Uncaught Exceptions**: Unhandled exceptions in threads can terminate the thread. Use `Thread.UncaughtExceptionHandler` to handle such cases.