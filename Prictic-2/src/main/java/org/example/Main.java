
package org.example;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Путь к CSV-файлу
        String filePath = "transactions.csv";

        // Чтение транзакций из CSV
        List<Transaction> transactions =
                TransactionCSVReader.readTransactions(filePath);

        // Расчёт общего баланса
        double totalBalance =
                TransactionAnalyzer.calculateTotalBalance(transactions);

        TransactionReportGenerator.printBalanceReport(totalBalance);

        // Поиск 10 самых больших расходов
        List<Transaction> topExpenses =
                TransactionAnalyzer.findTopExpenses(transactions);

        TransactionReportGenerator.printTopExpensesReport(topExpenses);

        // Подсчёт транзакций за выбранный месяц
        String monthYear = "01-2024";

        int transactionsCount =
                TransactionAnalyzer.countTransactionsByMonth(
                        transactions, monthYear
                );

        TransactionReportGenerator.printTransactionsCountByMonth(
                monthYear, transactionsCount
        );

        // Вывод всех транзакций
        System.out.println("\nУсі транзакції:");

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}
