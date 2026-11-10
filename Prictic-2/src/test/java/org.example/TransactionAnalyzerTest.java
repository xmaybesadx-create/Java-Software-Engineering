package org.example;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;


public class TransactionAnalyzerTest {
    @Test
    public void testCalculateTotalBalance() {
        // Створення тестових даних
        Transaction transaction1 = new Transaction("2023-01-01", 100.0, "Дохід");
        Transaction transaction2 = new Transaction("2023-01-02", -50.0, "Витрата");
        Transaction transaction3 = new Transaction("2023-01-03", 150.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        // Виклик методу, який потрібно протестувати
        double result = TransactionAnalyzer.calculateTotalBalance(transactions);

        // Перевірка результату
        Assertions.assertEquals(200.0, result, "Розрахунок загального балансу неправильний");
    }
    @Test
    public void testCountTransactionsByMonth() {
        // Підготовка тестових даних
        Transaction transaction1 = new Transaction("01-02-2023", 50.0, "Дохід");
        Transaction transaction2 = new Transaction("15-02-2023", -20.0, "Витрата");
        Transaction transaction3 = new Transaction("05-03-2023", 100.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);


        int countFeb = TransactionAnalyzer.countTransactionsByMonth(transactions, "02-2023");
        int countMar = TransactionAnalyzer.countTransactionsByMonth(transactions, "03-2023");

        // Перевірка результатів
        Assertions.assertEquals(2, countFeb, "Кількість транзакцій за лютий неправильна");
        Assertions.assertEquals(1, countMar, "Кількість транзакцій за березень неправильна");
    }
    @Test
    public void testFindTopExpenses() {

        // 1. Создаём тестовые транзакции
        Transaction transaction1 = new Transaction("01-01-2024", -500.0, "Продукти");
        Transaction transaction2 = new Transaction("02-01-2024", -1500.0, "Оренда");
        Transaction transaction3 = new Transaction("03-01-2024", 3000.0, "Заробіток");

        // 2. Объединяем транзакции в список
        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3);

        // 4. Получаем самые большие расходы
        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);

        // 5. Проверяем результат
        Assertions.assertEquals(2, topExpenses.size());
        Assertions.assertEquals(-1500.0, topExpenses.get(0).getAmount());
        Assertions.assertEquals(-500.0, topExpenses.get(1).getAmount());
    }

}

