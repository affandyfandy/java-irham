import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class RemoveDuplicate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String basePath = "Week 2/Unit 03/Assignment5-Lab/";
        String delimiter = ","; // Parse CSV format
        // Set to store unique keys (id)
        Set<String> seenKeys = new HashSet<>();

        System.out.print("Input file name: ");
        String inputFileName = basePath + scanner.nextLine();
        String outputFileName = basePath + "modified.csv";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) {
             
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into fields
                String[] fields = line.split(delimiter);
                
                // Ensure there are enough fields and key field is valid
                if (fields.length > 1) {
                    String key = fields[0]; // Assuming id is the first field
                    if (!seenKeys.contains(key)) {
                        seenKeys.add(key); // Add the key to set (marks as seen)
                        writer.println(line); // Write the line to output
                    }
                }
            }
            
            System.out.println("Duplicates removed successfully. Output written to " + outputFileName);
            
        } catch (IOException e) {
            System.out.println("I/O Error occured:" + e);
        }
    }
}