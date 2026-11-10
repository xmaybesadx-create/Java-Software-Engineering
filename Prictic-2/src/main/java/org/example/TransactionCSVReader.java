
package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {

    // Чтение строк из файла
    public static List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(filePath, StandardCharsets.UTF_8))) {

            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    // Преобразование строк в объекты Transaction
    public static List<Transaction> parseTransactions(List<String> lines) {
        List<Transaction> transactions = new ArrayList<>();

        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }

            String[] values = line.split(",", -1);

            if (values.length < 3) {
                System.out.println("Некоректна строка CSV: " + line);
                continue;
            }

            try {
                double amount = Double.parseDouble(values[1].trim());

                Transaction transaction = new Transaction(
                        values[0].trim(),
                        amount,
                        values[2].trim()
                );

                transactions.add(transaction);

            } catch (NumberFormatException e) {
                System.out.println("Помилка суми у строці: " + line);
            }
        }

        return transactions;
    }

    // Объединение чтения и обработки
    public static List<Transaction> readTransactions(String filePath) {
        List<String> lines = readLines(filePath);
        return parseTransactions(lines);
    }
}
