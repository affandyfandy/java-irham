import java.util.Scanner;

public class Lab2 {
    public static void main(String[] args) {
        // Initialize the array with 5 menu items
        String[] menu = {"1. Start", "2. Settings", "3. Help", "4. About", "5. Exit"};

        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        for (String item : menu) System.out.println(item);

        // Prompt the user to enter a menu number
        System.out.print("Enter the menu number (1-5): ");
        int menuNumber = scanner.nextInt();

        try {
        
            // Check if the user input is within the valid range
            if (menuNumber < 1 || menuNumber > 5) {
                throw new Lab2Exception("Invalid menu number. Please enter a number between 1 and 5.");
            }

            // Print the selected menu item
            System.out.println("You have selected: " + menu[menuNumber - 1]);

        } catch (Lab2Exception e) {
            // Handle custom Lab2Exception
            System.out.println(e.getLocalizedMessage());

        } catch (Exception e) {
            // Handle any other exceptions (e.g., input mismatch)
            System.out.println("An unexpected error occurred: " + e.getLocalizedMessage());

        } finally {
            // Close the scanner
            scanner.close();
        }
    }
}
