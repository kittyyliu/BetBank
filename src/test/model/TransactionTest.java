package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;

    @BeforeEach
    void setup() {
        transaction1 = new Transaction("abc123", 200, "baseball");
        transaction2 = new Transaction("abc1", 1, "tennis");
        transaction3 = new Transaction("abc23", 1000, "badminton");
    }

    @Test
    void testGetTransactionID() {
        assertEquals("abc123", transaction1.getTransactionID());
        assertEquals("abc1", transaction2.getTransactionID());
        assertEquals("abc23", transaction3.getTransactionID());
    }

    @Test
    void testGetBetAmount() {
        assertEquals(200, transaction1.getTransactionAmount());
        assertEquals(1, transaction2.getTransactionAmount());
        assertEquals(1000, transaction3.getTransactionAmount());
    }

    @Test
    void testBetType() {
        assertEquals("baseball", transaction1.getBetType());
        assertEquals("tennis", transaction2.getBetType());
        assertEquals("badminton", transaction3.getBetType());
    }

}
