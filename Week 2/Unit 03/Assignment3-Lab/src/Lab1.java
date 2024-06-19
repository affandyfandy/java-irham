import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Lab1 {
    public static void main(String[] args) {
        // Path to the input and output files
        Scanner sc = new Scanner(System.in);
        String basePath = "Week 2/Unit 03/Assignment3-Lab/";

        System.out.print("Input file name: ");
        String inputFile = basePath + sc.nextLine();
        String outputFile = basePath + "test2.txt";

        // Use try-with-resources to ensure resources are closed properly
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Print the content to the console
                System.out.println(line);

                // Write the content to the output file
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // Handle exceptions
            System.err.println("IOException occurred: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
