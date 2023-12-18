package com.maven2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class UserInput {

    private final BufferedReader reader;

    public UserInput() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public int getIntInput(String prompt) {
        try {
            System.out.print(prompt + " ");
            String input = reader.readLine();
            if (StringUtils.isNotBlank(input) && NumberUtils.isDigits(input)) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                return getIntInput(prompt);
            }
        } catch (IOException e) {
            System.out.println("Invalid input. Please try again.");
            e.printStackTrace();
            return getIntInput(prompt);
        }
    }

    public String getStringInput(String prompt) {
        try {
            System.out.print(prompt + " ");
            String input = reader.readLine();
            if (StringUtils.isNotBlank(input)) {
                return input;
            } else {
                System.out.println("Invalid input. Please try again.");
                return getStringInput(prompt);
            }
        } catch (IOException e) {
            System.out.println("Invalid input. Please try again.");
            e.printStackTrace();
            return "";
        }
    }

    public int getTableDimensionInput(String prompt) {
        try {
            System.out.print(prompt + " ");
            String input = reader.readLine();
            if (StringUtils.isNotBlank(input) && NumberUtils.isDigits(input)) {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
            }
            System.out.println("Invalid input. Please enter a number greater than 0.");
            return getTableDimensionInput(prompt);
        } catch (IOException e) {
            System.out.println("Invalid input. Please enter a number greater than 0.");
            e.printStackTrace();
            return getTableDimensionInput(prompt);
        }
    }

    public int charTypeInput(String prompt) {
        try {
            System.out.print(prompt + " ");
            String input = reader.readLine();
            if (StringUtils.isNotBlank(input) && NumberUtils.isDigits(input)) {
                int value = Integer.parseInt(input);
                if (value >= 1 && value <= 4) {
                    return value;
                }
            }
            System.out.println("Invalid input. Please enter a number between 1 and 4.");
            return charTypeInput(prompt);
        } catch (IOException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 4.");
            e.printStackTrace();
            return charTypeInput(prompt);
        }
    }
}
