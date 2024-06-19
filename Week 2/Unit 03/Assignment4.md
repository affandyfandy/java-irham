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

**[BankApplication](Assignment4-Lab/src/BankApplication.java)**
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