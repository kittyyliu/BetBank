package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BetTest {

    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;

    @BeforeEach
    void setup() {
        transaction1 = new Transaction("a", 200, "baseball");
        transaction2 = new Transaction("b", 1, "tennis");
        transaction3 = new Transaction("c", 1000, "badminton");
    }

    @Test
    void testGetTransactionID() {
        assertEquals("a", transaction1.getTransactionID());
        assertEquals("a", transaction1.getTransactionID());
        assertEquals("a", transaction1.getTransactionID());
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
