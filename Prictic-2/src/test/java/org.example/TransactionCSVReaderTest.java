package org.example;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TransactionCSVReaderTest {
    @Test
    public void testReadTransaction() {
        String filePath = "src/test/resources/test-transactions.csv";

        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        Assertions.assertEquals(2, transactions.size());
        Assertions.assertEquals(100.0, transactions.get(0).getAmount());
        Assertions.assertEquals("Зарплата", transactions.get(0).getDescription());
    }
}
