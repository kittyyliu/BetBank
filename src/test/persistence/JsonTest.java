package persistence;

import model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

// code referenced from JsonTest from JsonSerializationDemo
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected void checkTransaction (String id, int amount, String type) {
        Transaction transaction = new Transaction(id, amount, type);
        assertEquals(id, transaction.getTransactionID());
        assertEquals(amount, transaction.getTransactionAmount());
        assertEquals(type, transaction.getBetType());
    }
}
