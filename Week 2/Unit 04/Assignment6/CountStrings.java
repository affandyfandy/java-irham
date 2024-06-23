import java.util.Arrays;
import java.util.List;

public class CountStrings {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("Red", "Green", "Blue", "Pink", "Brown");
        String startingLetter = "G";

        // Using streams to count strings starting with the specific letter
        long count = strings.stream()
                            .filter(str -> str.startsWith(startingLetter))
                            .count();

        System.out.println("Number of strings starting with '" + startingLetter + "': " + count);
    }
}

