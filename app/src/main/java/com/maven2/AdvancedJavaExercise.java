package com.maven2;

import java.io.File;
import java.util.Map;

public class AdvancedJavaExercise {
    private static final String FILE_PATH = "src/main/resources/TABLE.txt";

    public static void main(String[] args) {
        Map<String, Map<String, String>> table;
        UserInput userInput = new UserInput();
        File tableFile = new File(FILE_PATH);
        if (tableFile.exists()) {
            table = FileManager.loadTableFromFile();
        } else {
            System.out.println("Error: File not found. Create a new table and file.");
            int rows = userInput.getTableDimensionInput("Enter the number of rows (x):");
            int columns = userInput.getTableDimensionInput("Enter the number of columns (y):");
            int charType = TableGenerator.defaultCharTypeValue();
            table = TableGenerator.generateTable(rows, columns, charType);
            FileManager.saveTableToFile(table, tableFile);
        }

        TableManager tableManager = new TableManager(table);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Search");
            System.out.println("2. Edit");
            System.out.println("3. Print");
            System.out.println("4. Reset");
            System.out.println("5. Add Row");
            System.out.println("6. Sort");
            System.out.println("7. Exit");
            int choice = userInput.getIntInput("Choose an option (1-7): ");

            switch (choice) {
                case 1:
                    tableManager.searchStringTable();
                    break;

                case 2:
                    tableManager.editCell();
                    FileManager.saveTableToFile(tableManager.getTable(), tableFile);
                    break;

                case 3:
                    tableManager.printTable();
                    break;

                case 4:
                    tableManager.resetTable();
                    FileManager.saveTableToFile(tableManager.getTable(), tableFile);
                    break;
                case 5:
                    tableManager.addRow();
                    FileManager.saveTableToFile(tableManager.getTable(), tableFile);
                    break;
                case 6:
                    tableManager.sortRows();
                    FileManager.saveTableToFile(tableManager.getTable(), tableFile);
                    break;
                case 7:
                    System.out.println("Exiting the program. Goodbye!");
                    FileManager.saveTableToFile(tableManager.getTable(), tableFile);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }
}
