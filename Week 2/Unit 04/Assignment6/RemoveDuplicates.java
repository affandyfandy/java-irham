import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveDuplicates {
    public static void main(String[] args) {
        // Creating a list of strings with duplicates
        List<String> strings = Arrays.asList("apple", "banana", "apple", "orange", "banana", "grape");
        
        // Removing duplicates using streams
        List<String> uniqueStrings = strings.stream()
                                            .distinct()
                                            .collect(Collectors.toList());
        
        // Printing the result
        System.out.println("List with duplicates removed: " + uniqueStrings);
    }
}
