package persistence;

import model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTransaction (String id, int amount, String type) {
        Transaction transaction = new Transaction(id, amount, type);
        assertEquals(id, transaction.getTransactionID());
        assertEquals(amount, transaction.getTransactionAmount());
        assertEquals(type, transaction.getBetType());
    }
}
