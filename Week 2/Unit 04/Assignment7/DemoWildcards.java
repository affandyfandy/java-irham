import java.util.ArrayList;
import java.util.List;

public class DemoWildcards {
    public static void main(String[] args) {
        // Box of integers
        Box<Integer> integerBox = new Box<>(10);
        printBox(integerBox);

        // Box of strings
        Box<String> stringBox = new Box<>("Hello, World!");
        printBox(stringBox);

        // List of boxes with wildcard
        List<Box<?>> boxes = new ArrayList<>();
        boxes.add(integerBox);
        boxes.add(stringBox);

        // Print contents of boxes
        System.out.println("Contents of boxes:");
        printBoxes(boxes);
    }

    // Method to print the contents of a specific box
    public static void printBox(Box<?> box) {
        System.out.println("Box contents: " + box.getItem());
    }

    // Method to print the contents of a list of boxes
    public static void printBoxes(List<Box<?>> boxes) {
        for (Box<?> box : boxes) {
            System.out.println(box);
        }
    }
}


class Box<T> {
    private T item;

    public Box(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Box{" +
                "item=" + item +
                '}';
    }
}
