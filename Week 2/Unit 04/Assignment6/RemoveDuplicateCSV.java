import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveDuplicateCSV {
    public static void main(String[] args) {
        String inputFilePath = "Week 2/Unit 04/Assignment6/data/fruits.csv";
        String outputFilePath = "Week 2/Unit 04/Assignment6/data/modified.csv";
        String keyFieldName = "id";

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            if (lines.isEmpty()) return;

            String header = lines.get(0);
            List<String> headers = Arrays.asList(header.split(","));
            int keyIndex = headers.indexOf(keyFieldName);
            if (keyIndex == -1) throw new IllegalArgumentException("Invalid key field name");

            List<String> uniqueLines = lines.stream()
                                            .skip(1) // Skip header
                                            .collect(Collectors.toMap(
                                                line -> line.split(",")[keyIndex],
                                                line -> line,
                                                (existing, replacement) -> existing
                                            ))
                                            .values()
                                            .stream()
                                            .collect(Collectors.toList());


            uniqueLines.add(0, header);

            Files.write(Paths.get(outputFilePath), uniqueLines);
        } catch (IOException e) {
            System.out.println("I/O Error occured:" + e);
        }
    }
}