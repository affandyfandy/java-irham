import java.util.Scanner;

public class Lab3 {

    // Method to check for vowels in the input string
    public static void checkForVowels(String input) throws Lab3Exception {
        if (!input.matches(".*[AEIOUaeiou].*")) {
            throw new Lab3Exception("The string does not contain any vowels.");
        }
    }

    public static void main(String[] args) {
        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter a string
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        try {
            // Check for vowels in the input string
            checkForVowels(input);
            // Print a success message if the string contains vowels
            System.out.println("The string contains vowels.");
        } catch (Lab3Exception e) {
            // Handle the custom Lab3Exception
            System.out.println(e.getMessage());
        } finally {
            // Close the scanner
            scanner.close();
        }
    }
}
