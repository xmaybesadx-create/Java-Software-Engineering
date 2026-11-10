
package org.example;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Метод для розрахунку загального балансу
    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;

        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }

        return balance;
    }

    // Метод для пошуку 10 найбільших витрат
    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    // Метод для підрахунку транзакцій за конкретний місяць і рік
    public static int countTransactionsByMonth(
            List<Transaction> transactions,
            String monthYear
    ) {
        int count = 0;

        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.parse(
                    transaction.getDate(),
                    DATE_FORMATTER
            );

            String transactionMonthYear =
                    date.format(DateTimeFormatter.ofPattern("MM-yyyy"));

            if (transactionMonthYear.equals(monthYear)) {
                count++;
            }
        }

        return count;
    }
}
