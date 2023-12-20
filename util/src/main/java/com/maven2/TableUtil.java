package com.maven2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableUtil {
    public boolean isValidIndices(String rowIndex, int columnIndex, Map<String, Map<String, String>> table) {
        if (table.containsKey(rowIndex)) {
            Map<String, String> innerMap = table.get(rowIndex);
            List<String> columns = new ArrayList<>(innerMap.keySet());
            return columnIndex >= 0 && columnIndex < columns.size();
        }
        return false;
    }

    public boolean isNewKeyValid(Map<String, String> innerMap, String newChars, boolean isKeyChange,
            String selectedKey, Map<String, Map<String, String>> table) {
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

    public void performKeyChange(String rowKey, Map<String, String> innerMap, List<String> columns, String selectedKey,
            String selectedValue, String newChars, Map<String, Map<String, String>> table) {
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

    public void performValueChange(Map<String, String> innerMap, String selectedKey, String newChars) {
        String selectedValue = innerMap.get(selectedKey);
        if (newChars.length() == selectedValue.length()) {
            innerMap.put(selectedKey, newChars);
            System.out.println("Edit successful. \nUpdated Table:");
        } else {
            System.out.println("Error: The length of the input does not match the length of the selected value.");
        }
    }

    public int countInstances(String input, String searchSequence) {
        int count = 0;
        int index = input.indexOf(searchSequence);

        while (index != -1) {
            count++;
            index = input.indexOf(searchSequence, index + 1);
        }

        return count;
    }
}
