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
