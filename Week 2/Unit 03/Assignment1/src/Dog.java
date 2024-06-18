public class Dog {
    private String color;
    private String name;
    private String breed;

    public Dog(String color, String name, String breed) {
        this.color = color;
        this.name = name;
        this.breed = breed;
    }

    public static void main(String[] args) {
        Dog dog = new Dog("Black", "Blacky", "Husky");

        System.out.println("I have a " + dog.breed);
        System.out.println("His color is " + dog.color);
        System.out.println("Say hello, " + dog.name + "!");
        System.out.print("Blacky: ");
        dog.bark();
        System.out.println("Good boy! Now here's your prize!");
        System.out.print("Blacky: ");
        dog.eat();
        System.out.print("Blacky: ");
        dog.wagTail();
    }

    public void wagTail() {
        System.out.println("Wagging the tail..");
    }

    public void bark() {
        System.out.println("Woof woof!");
    }

    public void eat() {
        System.out.println("Eating..");
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBreed() {
        return breed;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }

    
}
