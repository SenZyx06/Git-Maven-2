package com.maven2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileManager {
    private static final String FILE_PATH = "src/main/resources/TABLE.txt";

    public static Map<String, Map<String, String>> loadTableFromFile() {
        File file = new File(FILE_PATH);
        System.out.println("Searching for file " + file.getName());
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            Map<String, Map<String, String>> table = new LinkedHashMap<>();

            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("║");
                List<String> processedStrings = Arrays.stream(tokens)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());

                Map<String, String> cell = new LinkedHashMap<>();
                for (int i = 0; i < processedStrings.size(); i++) {
                    String[] keyValue = processedStrings.get(i).split(" ¡ ");
                    String key = keyValue[0];
                    String value = keyValue[1];

                    if (key.length() != 3 || value.length() != 3) {
                        throw new IllegalArgumentException();
                    }

                    cell.put(key, value);
                }

                table.put(Integer.toString(table.size()), cell);
            }
            System.out.println("Table has been Imported");
            return table;
        } catch (Exception e) {
            UserInput userInput = new UserInput();
            System.out.println("Error loading table from file.");
            while (true) {
                int choice = userInput.getIntInput(
                        "1. Generate new table\n2. Exit Program\nWould you like to generate a new Table?: ");
                switch (choice) {
                    case 1:
                        System.out.println("Creating a new random table.");
                        return TableGenerator.generateTable(3, 3, 4);
                    case 2:
                        System.out.println("Exiting the program. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Error! Please choose between 1 and 2");
                }
            }
        }
    }

    public static void saveTableToFile(Map<String, Map<String, String>> table, File file) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            for (Map.Entry<String, Map<String, String>> entry : table.entrySet()) {
                writer.write("║");
                for (Map.Entry<String, String> cellEntry : entry.getValue().entrySet()) {
                    writer.write(cellEntry.getKey() + " ¡ " + cellEntry.getValue() + "║");
                }
                writer.write("\n");
            }
            System.out.println("Table has been saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving table to file: " + e.getMessage());
        }
    }

}
