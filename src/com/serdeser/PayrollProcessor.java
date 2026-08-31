package com.serdeser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PayrollProcessor {
    public static void main(String[] args) {
        Path csvPath = Paths.get("src/employees.csv");

        try (BufferedReader reader = Files.newBufferedReader(csvPath)) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                // Skip the first row containing column headers
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Split the line by comma delimiter
                String[] data = line.split(",");

                // Extract fields based on column position
                String id = data[0];
                String name = data[1];
                String dept = data[2];
                double baseSalary = Double.parseDouble(data[3]);

                // Apply business logic (10% Bonus)
                double totalPayout = baseSalary * 1.10;

                System.out.printf("Processed -> ID: %s | Name: %-10s | Dept: %-11s | Total Payout: Rs%.2f%n",
                        id, name, dept, totalPayout);
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numeric data: " + e.getMessage());
        }
    }
}

