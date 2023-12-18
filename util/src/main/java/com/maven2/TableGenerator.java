package com.maven2;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

public class TableGenerator {
    public static Map<String, Map<String, String>> generateTable(int rows, int columns, int charType) {
        Map<String, Map<String, String>> table = new LinkedHashMap<>();
        for (int i = 0; i < rows; i++) {
            Map<String, String> cell = new LinkedHashMap<>();
            for (int j = 0; j < columns; j++) {
                String key = generateRandomString(charType);
                String value = generateRandomString(charType);
                cell.put(key, value);
                table.put(Integer.toString(i), cell);
            }
        }
        return table;
    }

    public static String generateRandomString(int charType) {
        int charLength = 3;

        switch (charType) {
            case 1:
                return RandomStringUtils.randomAlphabetic(charLength).toUpperCase();
            case 2:
                return RandomStringUtils.randomAlphabetic(charLength).toLowerCase();
            case 3:
                return RandomStringUtils.randomAlphabetic(charLength);
            case 4:
                return RandomStringUtils.randomAscii(charLength);
            default:
                throw new IllegalArgumentException("Invalid choice");
        }
    }

    public static int defaultCharTypeValue() {
        return 4;
    }
}
