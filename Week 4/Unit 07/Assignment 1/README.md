# Assignment 1
## Task 1: Dependency Injection Advantages & Drawback

### Advantages
#### 1. Decoupling
Dependency Injection (DI) promotes loose coupling between classes. By injecting dependencies, classes are not responsible for creating their own dependencies, making them easier to manage and modify independently.

**Example**
```java
public class Service {
    private final Repository repository;

    @Autowired
    public Service(Repository repository) {
        this.repository = repository;
    }
}
```

#### 2. Improved Testability
DI makes unit testing easier by allowing mock dependencies to be injected, facilitating isolated testing of classes.

**Example**
```java
@Mock
private Repository repository;

@InjectMocks
private Service service;
```

#### 3. Easier Maintenance
With DI, changes in dependency implementations require minimal changes in the classes that use them, simplifying maintenance and upgrades.

**Example**
```java
// Switching from MySQLRepository to MongoDBRepository
public class AppConfig {
    @Bean
    public Repository repository() {
        return new MongoDBRepository();
    }
}
```

#### 4. Configuration Flexibility
DI allows for flexible configuration of dependencies, whether through XML, annotations, or Java configuration.

**Example**
```java
@Configuration
public class AppConfig {
    @Bean
    public Service service() {
        return new Service(repository());
    }

    @Bean
    public Repository repository() {
        return new MySQLRepository();
    }
}
```
#### 5. Promotes Reusability
By injecting dependencies, classes become more reusable and can be easily integrated into different contexts or applications.

**Example**
```java
public class NotificationService {
    private final EmailService emailService;

    @Autowired
    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }
}
```

### Drawbacks
#### 1. Complexity
DI can introduce additional complexity, especially for developers new to the concept, making it harder to understand and debug the application flow.

**Example**
```java
@Autowired
private Service service;
```

#### 2. Performance Overhead
The DI framework adds a performance overhead due to the creation and management of the dependency graph, which can impact the application's startup time.

**Example**
```java
@ComponentScan(basePackages = "com.example")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

#### 3. Potential for Misconfiguration
Misconfigurations can lead to runtime errors that can be difficult to trace, especially if dependencies are not properly defined or wired.

**Example**
```java
@Component
public class MyComponent {
    // Missing @Autowired annotation
    private MyService myService;
}
```

#### 4. Overhead of Dependency Management
Managing a large number of beans and their dependencies can become cumbersome, requiring careful planning and organization.

**Example**
```java
@Configuration
public class ComplexConfig {
    @Bean
    public Service service() {
        return new Service(repository(), cache(), logger());
    }

    @Bean
    public Repository repository() {
        return new MySQLRepository();
    }

    @Bean
    public Cache cache() {
        return new RedisCache();
    }

    @Bean
    public Logger logger() {
        return new ConsoleLogger();
    }
}
```

#### 5. Hidden Dependecies
Dependencies may be hidden in configurations, making it less obvious what a class's actual dependencies are, which can complicate understanding the codebase.

---

Overall, while Dependency Injection offers significant benefits in terms of modularity, testability, and maintainability, it also introduces certain challenges that need to be managed carefully to ensure a clean and efficient codebase.


## Task 2: Convert XML into Java Bean (with Constructor Injection)
You can see the full project [here](lab/src/main/java/fpt/lab/LabApplication.java).

To convert xml into Java Bean, first we need to add configuration class. For [example](lab/src/main/java/fpt/lab/config/AppConfig.java):
```java
public class AppConfig {
    
    @Bean
    public EmployeeWork employeeWork() {
        return new EmployeeWork();
    }

    @Bean
    public Employee employee() {
        return new Employee("Irham", employeeWork());
    }
}
```

This is the configuration class where we define the beans using `@Bean` annotation.

- `employeeWork()` method creates and returns an instance of `EmployeeWork`.
- `employee()` method creates and returns an instance of `Employee` by injecting the name and the `EmployeeWork` bean.

Then, we change the code on the main App using config class that we've made. For [example](lab/src/main/java/fpt/lab/LabApplication.java):
```java
@SpringBootApplication
public class LabApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabApplication.class, args);

		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Employee employee = context.getBean("employee", Employee.class);
		employee.working();
	}

}
```
- It initializes the Spring Application context using `AnnotationConfigApplicationContext`.
- Retrieves the `Employee` bean and calls the `working()` method.

## Task 3: Using Setter & Field Injection
### Using Setter Injection
This time, I created new class to demonstrate the setter injection. Here's [the code](lab/src/main/java/fpt/lab/model/EmployeeSetter.java):
```java
public class EmployeeSetter {
    private String name;
    private EmployeeWork employeeWork;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployeeWork(EmployeeWork employeeWork) {
        this.employeeWork = employeeWork;
    }

    // Method
    public void working() {
        System.out.println("My Name is: " + name);
        employeeWork.work();
    }
}
```
This class contains 2 setters to set the value for `name` and `employeeWork`.

After created the model, next we need to modify the configuration like this [code](lab/src/main/java/fpt/lab/config/SetterConfig.java):
```java
public class SetterConfig {
    
    @Bean
    public EmployeeWork employeeWork() {
        return new EmployeeWork();
    }

    @Bean
    public EmployeeSetter employee() {
        EmployeeSetter employee = new EmployeeSetter();
        System.out.println("WITH SETTER");
        employee.setName("Irham");
        employee.setEmployeeWork(employeeWork());
        return employee;
    }
}
```

On `employee` method, we inject the value for employee's name and work with its setter methods.

### Using Field Injection
On this [example](lab/src/main/java/fpt/lab/model/EmployeeField.java), I modify the `Employee` model with `@AutoWired` annotation to automatically inject the `EmployeeWork`.
```java
public class EmployeeField {
    private String name;

    @Autowired
    private EmployeeWork employeeWork;

    public void setName(String name) {
        this.name = name;
    }

    // Method
    public void working() {
        System.out.println("My Name is: " + name);
        employeeWork.work();
    }
}
```

Next, this is how to call the class with field injection. [Example](lab/src/main/java/fpt/lab/config/FieldConfig.java):
```java
public class FieldConfig {
    
    @Bean
    public EmployeeWork employeeWork() {
        return new EmployeeWork();
    }

    @Bean
    public EmployeeField employee() {
        EmployeeField employee = new EmployeeField();
        employee.setName("Irham");
        return employee;
    }
}
```

We just need to create the object of the class and it's automatically inject the `EmployeeWork`.