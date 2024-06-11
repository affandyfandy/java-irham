# OOP Principles in Java

### 1. Encapsulation
Encapsulation controlls the access to the data inside of the class. This protects the internal state of an object and ensures that it can only be accessed through specific class.

**Example**:

```java
public class EncapsulationExample {
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        }
    }

    public static void main(String[] args) {
        EncapsulationExample obj = new EncapsulationExample();
        obj.age = 10; // It can't be accessed like this because the attribute has set to private
        obj.setAge(30); // Setting age using setter method
        System.out.println("Age: " + obj.getAge()); // Getting age using getter method
    }
}
```

### 2. Inheritance
Inheritance enables a class to inherit properties and behaviors from another class. This promotes code reuse and supports the concept of "is-a" relationship.

```java
class Vehicle {
    void drive() {
        System.out.println("Vehicle is being driven");
    }
}

class Car extends Vehicle {
    void honk() {
        System.out.println("Car is honking");
    }
}

public class InheritanceExample {
    public static void main(String[] args) {
        Car car = new Car();
        car.drive(); // Output: Vehicle is being driven
        car.honk(); // Output: Car is honking
    }
}
```

### 3. Polymorphism
Polymorphism allows objects to be treated as instances of their parent class. This enables different implementations of methods based on the object's type.

```java
interface Animal {
    void makeSound();
}

class Dog implements Animal {
    public void makeSound() {
        System.out.println("Dog barks");
    }
}

class Cat implements Animal {
    public void makeSound() {
        System.out.println("Cat meows");
    }
}

public class PolymorphismExample {
    public static void main(String[] args) {
        Animal animal1 = new Dog();
        Animal animal2 = new Cat();

        animal1.makeSound(); // Output: Dog barks
        animal2.makeSound(); // Output: Cat meows
    }
}
```

### 4. Abstraction
Abstraction involves hiding the implementation details of a class and showing only essential features.

```java
abstract class Shape {
    abstract void draw();
}

class Circle extends Shape {
    void draw() {
        System.out.println("Drawing a circle");
    }
}

class Rectangle extends Shape {
    void draw() {
        System.out.println("Drawing a rectangle");
    }
}

public class AbstractionExample {
    public static void main(String[] args) {
        Shape shape1 = new Circle();
        Shape shape2 = new Rectangle();

        shape1.draw(); // Output: Drawing a circle
        shape2.draw(); // Output: Drawing a rectangle
    }
}
```

