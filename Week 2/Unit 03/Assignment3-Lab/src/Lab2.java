import java.util.Scanner;

public class Lab2 {
    public static void main(String[] args) {
        // Initialize the array with 5 menu items
        String[] menu = {"Menu 1: Start", "Menu 2: Settings", "Menu 3: Help", "Menu 4: About", "Menu 5: Exit"};

        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        try {
            // Prompt the user to enter a menu number
            System.out.print("Enter the menu number (1-5): ");
            int menuNumber = scanner.nextInt();

            // Check if the user input is within the valid range
            if (menuNumber < 1 || menuNumber > 5) {
                throw new Lab2Exception("Invalid menu number. Please enter a number between 1 and 5.");
            }

            // Print the selected menu item
            System.out.println(menu[menuNumber - 1]);

        } catch (Lab2Exception e) {
            // Handle custom Lab2Exception
            System.out.println(e.getMessage());

        } catch (Exception e) {
            // Handle any other exceptions (e.g., input mismatch)
            System.out.println("An unexpected error occurred: " + e.getMessage());

        } finally {
            // Close the scanner
            scanner.close();
        }
    }
}
