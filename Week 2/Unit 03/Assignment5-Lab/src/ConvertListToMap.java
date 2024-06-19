import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConvertListToMap {
    
    public static void main(String[] args) {
        try {
            List<Map<String, String>> list = readCSV();
            Map<Integer, Map<String, String>> map = convertListToMap(list);
            boolean isAscending = getUserInput();
            Map<Integer, Map<String, String>> sortedMap = sortByKey(map, isAscending);
            printSortedMap(sortedMap);
        } catch (IOException e) {
            System.out.println("Error occured: " + e.getMessage());
        }
    }

    private static boolean getUserInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Select sorting order:");
                System.out.println("1. ASCENDING");
                System.out.println("2. DESCENDING");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> {
                        return true;
                    }
                    case 2 -> {
                        return false;
                    }
                    default -> System.out.println("Invalid input. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private static List<Map<String, String>> readCSV() throws IOException {
        String inputFile = "Week 2/Unit 03/Assignment5-Lab/modified.csv";
        List<Map<String, String>> list;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            list = new ArrayList<>();
            String headerLine = reader.readLine();
            String[] headers = headerLine.split(",");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    map.put(headers[i], values[i]);
                }
                list.add(map);
            }
        }
        return list;
    }

    private static Map<Integer, Map<String, String>> convertListToMap(List<Map<String, String>> list) {
        Map<Integer, Map<String, String>> map = new HashMap<>();
        for (Map<String, String> item : list) {
            int id = Integer.parseInt(item.get("id"));
            map.put(id, item);
        }
        return map;
    }

    private static Map<Integer, Map<String, String>> sortByKey(Map<Integer, Map<String, String>> map, boolean  isDescending) {
        Comparator<Map.Entry<Integer, Map<String, String>>> comparator = Map.Entry.comparingByKey();
        if (isDescending) {
            comparator = comparator.reversed();
        }

        return map.entrySet()
                  .stream()
                  .sorted(comparator)
                  .collect(Collectors.toMap(
                      Map.Entry::getKey,
                      Map.Entry::getValue,
                      (e1, e2) -> e1,
                      LinkedHashMap::new
                  ));
    }

    private static void printSortedMap(Map<Integer, Map<String, String>> sortedMap) {
        for (Map.Entry<Integer, Map<String, String>> entry : sortedMap.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
