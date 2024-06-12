# Thread-Safe Singleton
**Thread-Safe Singleton** is an implementation of Singleton design that best for multi-threaded environment, where it has multiple threads that can access Singleton at the same time.

> 🎯 Make sure that **only one** instance that created, even when there are several thread that access it at the same time.

## Implementation
Based on my research, there are some method to implement thread-safe singleton.

### 1. Synchronized Method
This is the very simple implementation of thread-safe singleton. `getInstance()` method implemented using `synchronized` to make sure that only one thread can access it at one time.

> The `synchronized` keyword in Java is used to control access to a block of code or an object by multiple threads. It ensures that only one thread can execute a synchronized block or method at a time, thus preventing race conditions and ensuring thread safety.

```java
public class Synchronized {
    private static Synchronized instance;

    // Private constructor to pervent instantiation from other class
    private Synchronized() {

    }

    public static synchronized Synchronized getInstance() {
        if (instance == null) {
            return new Synchronized();
        }
        return instance;
    }

    // Stored attributes and methods here...
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
```
**Cons**: Though we have to keep the data integrity and prevent race condition, `synchronized` syntax will make some cost called `Synchronization Overhead`. This will happen when there are multiple threads that call the `getInstance()` at the same time.

### 2. Double-Checked Locking
This is more efficient implementation, using double checking to prevent synchronization overhead.

```java
public class DoubleCheckLocking {
    private  static volatile DoubleCheckLocking instance;

    // Private constructor to pervent instantiation from other class
    private DoubleCheckLocking() {

    }

    public static DoubleCheckLocking getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckLocking.class) {
                if (instance == null) {
                    return new DoubleCheckLocking();
                }
            }
        }
        return instance;
    }

    // Stored attributes and methods here...
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
```

Thread-safety has guaranteed by `synchronized` and `volatile` syntax.
> The volatile keyword in Java is used to indicate that a variable's value will be modified by different threads. Declaring a variable as volatile ensures that changes to its value are always visible to other threads. This prevents threads from caching the variable value and ensures that they read the most up-to-date value.

### 3. Bill Pugh Singleton
This implements inner static helper class, which guarantee the thread-safe by `class loading` mechanism in Java. This also using lazy initialization without synchronization overhead that causes blocking.

> `class loading` refers to the mechanism by which Java loads classes into memory at runtime.

```java
public class BillPugh {
    private BillPugh() {

    }

    private static class SingletonHelper {
        private static final BillPugh instance = new BillPugh();
    }

    public static BillPugh getInstance() {
        return SingletonHelper.instance;
    }

    // Stored attributes and methods here...
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
```

### 4. Enum Method
This is the easiest method to implement thread-safe singleton. Java's singleton implementation of Enum is thread-safe by default (handled by Java Virtual Machine), so we don't have to worry about it. All we need is just make an enum with one case `INSTANCE`.

```java
public enum EnumSingleton {
    INSTANCE;

    // Stored attributes and methods here...
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
```
<br>

## Comparison
Method   |   Simplicity  |   Performance   
---------|---------------|--------------
Synchronized   |   Moderate   | Low (due to syncrhonization overhead)
Double-Checked Locking   |   Complex   | Moderate (lower overhead)
Bill Pugh   |   Simple   |   High (no synchronization overhead)
Enum   |   Very Simple   |   High (handled by JVM)