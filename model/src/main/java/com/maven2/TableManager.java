package com.maven2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableManager {
    private Map<String, Map<String, String>> table;
    private int charType = TableGenerator.defaultCharTypeValue();

    public TableManager(Map<String, Map<String, String>> table) {
        this.table = table;
    }

    UserInput userInput = new UserInput();

    public void searchStringTable() {
        String searchString = userInput.getStringInput("Enter the character sequence: ");
        if (searchString.isEmpty()) {
            searchString = " ";
        }
        int totalInstances = 0;

        for (Map.Entry<String, Map<String, String>> entry : table.entrySet()) {
            String rowIndex = entry.getKey();
            Map<String, String> innerMap = entry.getValue();
            int columnIndex = 0;

            for (Map.Entry<String, String> innerEntry : innerMap.entrySet()) {
                String key = innerEntry.getKey();
                String value = innerEntry.getValue();

                int keyInstances = countInstances(key, searchString);
                int valueInstances = countInstances(value, searchString);

                if (keyInstances > 0) {
                    System.out.println("Found \"" + searchString + "\" on (" + rowIndex + ", "
                            + columnIndex + ") with " + keyInstances + " instance(s) in Key");
                    totalInstances += keyInstances;
                }

                if (valueInstances > 0) {
                    System.out.println("Found \"" + searchString + "\" on (" + rowIndex + ", "
                            + columnIndex + ") with " + valueInstances + " instance(s) in Value");
                    totalInstances += valueInstances;
                }

                columnIndex++;
            }
        }
        System.out.println("Total instances: " + totalInstances);
    }

    public void editCell() {
        int rowIndex = userInput.getIntInput("Enter the row index: ");
        String rowKey = Integer.toString(rowIndex);

        if (table.containsKey(rowKey)) {
            Map<String, String> row = table.get(rowKey);
            int noOfColumns = row.size();
            int columnIndex = userInput.getIntInput("Enter the column index: ");
            if (columnIndex >= 0 && columnIndex < noOfColumns) {
                int editKeyOrValue = userInput.getIntInput("Choose which to edit (1 - Key, 2 - Value): ");

                if (isValidIndices(rowKey, columnIndex)) {
                    Map<String, String> innerMap = table.get(rowKey);
                    List<String> columns = new ArrayList<>(innerMap.keySet());
                    if (columnIndex >= 0 && columnIndex < columns.size()) {
                        if (editKeyOrValue == 1 || editKeyOrValue == 2) {
                            boolean isKeyChange = (editKeyOrValue == 1);
                            String selectedKey = columns.get(columnIndex);
                            String selectedValue = innerMap.get(selectedKey);
                            String newChars = userInput.getStringInput(
                                    "Enter the new characters (length: " + selectedKey.length() + "): ");

                            if (isNewKeyValid(innerMap, newChars, isKeyChange, selectedKey)) {
                                if (isKeyChange) {
                                    performKeyChange(rowKey, innerMap, columns, selectedKey, selectedValue, newChars);
                                } else {
                                    performValueChange(innerMap, selectedKey, newChars);
                                }
                            }
                        } else {
                            System.out.println("Error: Invalid choice. Please choose either 1 or 2.");
                        }
                    } else {
                        System.out.println("Error: Invalid column input. Please enter a valid column index.");
                    }
                } else {
                    System.out.println("Error: Invalid column input. Please enter a valid column index.");
                }
            } else {
                System.out.println("Error: Invalid column input. Please enter a valid column index.");
            }
        } else {
            System.out.println("Error: Invalid input. Please enter a valid row index.");
        }
    }

    public void printTable() {
        for (Map.Entry<String, Map<String, String>> entry : table.entrySet()) {
            System.out.print("║");
            for (Map.Entry<String, String> cellEntry : entry.getValue().entrySet()) {
                System.out.print(cellEntry.getKey() + " ¡ " + cellEntry.getValue() + "║");
            }
            System.out.println();
        }
    }

    public void resetTable() {
        int newRow = userInput.getTableDimensionInput("Enter the new number of rows: ");
        int newCol = userInput.getTableDimensionInput("Enter the new number of columns: ");
        int newCharType = userInput.charTypeInput(
                "\n1. Uppercase Letters\n2. Lowercase\n3. Mix of upper and lower\n4. ASCII table\nChoose character type: ");

        charType = newCharType;
        table = TableGenerator.generateTable(newRow, newCol, charType);
        System.out.println("Reset successful. Updated Table:");

    }

    public void addRow() {
        int rowIndex = userInput.getIntInput("Enter the index to add a new row (0-" + (table.size()) + "): ");
        if (rowIndex < 0 || rowIndex > table.size()) {
            System.out.println("Invalid index. Please enter a valid index.");
            addRow();
            return;
        } else {
            charType = userInput.charTypeInput(
                    "1. Uppercase Letters\n2. Lowercase\n3. Mix of upper and lower\n4. ASCII table\nChoose character type: ");

            Map<String, String> newRow = new LinkedHashMap<>();
            for (int j = 0; j < table.entrySet().iterator().next().getValue().size(); j++) {
                String key = TableGenerator.generateRandomString(charType);
                String value = TableGenerator.generateRandomString(charType);
                newRow.put(key, value);
            }

            List<Map<String, String>> newTable = new ArrayList<>(table.values());
            newTable.add(rowIndex, newRow);

            int i = 0;
            table.clear();
            for (Map<String, String> row : newTable) {
                table.put(Integer.toString(i), row);
                i++;
            }

            System.out.println("New row added successfully!");
        }

    }

    public void sortRows() {
        for (Map.Entry<String, Map<String, String>> entry : table.entrySet()) {
            Map<String, String> row = entry.getValue();
            List<Map.Entry<String, String>> list = new ArrayList<>(row.entrySet());
            list.sort(Comparator.comparing(e -> e.getKey() + e.getValue()));

            Map<String, String> sortedRow = new LinkedHashMap<>();
            for (Map.Entry<String, String> sortedEntry : list) {
                sortedRow.put(sortedEntry.getKey(), sortedEntry.getValue());
            }

            entry.setValue(sortedRow);
        }

        System.out.println("Table rows sorted!");
    }

    private boolean isValidIndices(String rowIndex, int columnIndex) {
        if (table.containsKey(rowIndex)) {
            Map<String, String> innerMap = table.get(rowIndex);
            List<String> columns = new ArrayList<>(innerMap.keySet());
            return columnIndex >= 0 && columnIndex < columns.size();
        }
        return false;
    }

    private boolean isNewKeyValid(Map<String, String> innerMap, String newChars, boolean isKeyChange,
            String selectedKey) {
        boolean isNewKeyAlreadyExists = table.values().stream()
                .anyMatch(map -> map.containsKey(newChars));

        if (isNewKeyAlreadyExists) {
            System.out.println("Error: The new key already exists in the table.");
            return false;
        }

        if (isKeyChange && innerMap.containsKey(newChars)
                && !innerMap.get(newChars).equals(innerMap.get(selectedKey))) {
            System.out.println("Error: The new key already exists in the current row.");
            return false;
        }

        return true;
    }

    private void performKeyChange(String rowKey, Map<String, String> innerMap, List<String> columns, String selectedKey,
            String selectedValue, String newChars) {
        if (newChars.length() == selectedKey.length()) {
            Map<String, String> updatedMap = new LinkedHashMap<>();
            for (String column : columns) {
                if (column.equals(selectedKey)) {
                    updatedMap.put(newChars, selectedValue);
                } else {
                    updatedMap.put(column, innerMap.get(column));
                }
            }
            table.put(rowKey, updatedMap);
            System.out.println("Edit successful. \nUpdated Table:");
        } else {
            System.out.println("Error: The length of the input does not match the length of the selected key.");
        }
    }

    private void performValueChange(Map<String, String> innerMap, String selectedKey, String newChars) {
        String selectedValue = innerMap.get(selectedKey);
        if (newChars.length() == selectedValue.length()) {
            innerMap.put(selectedKey, newChars);
            System.out.println("Edit successful. \nUpdated Table:");
        } else {
            System.out.println("Error: The length of the input does not match the length of the selected value.");
        }
    }

    private int countInstances(String input, String searchSequence) {
        int count = 0;
        int index = input.indexOf(searchSequence);

        while (index != -1) {
            count++;
            index = input.indexOf(searchSequence, index + 1);
        }

        return count;
    }

    public Map<String, Map<String, String>> getTable() {
        return this.table;
    }

}
