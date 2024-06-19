# Assignment 4
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

