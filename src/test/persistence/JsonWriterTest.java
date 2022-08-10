package persistence;

import model.Account;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// code referenced from JsonWriterTest from JsonSerializationDemo
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Account acc = new Account("hello", 20);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccount() {
        try {
            Account acc = new Account("MONEYMAKER222", 0);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json");
            acc = reader.read();
            assertEquals("MONEYMAKER222", acc.getUsername());
            assertEquals(0, acc.getBettingHistory().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterAccount() {
        try {
            Account acc = new Account("MONEYMAKER222", 2);
            acc.addTransaction(new Transaction( "abc123", 150, "Deposit"));
            acc.addTransaction(new Transaction("abc123", 50, "basketball"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json");
            acc = reader.read();
            assertEquals("MONEYMAKER222", acc.getUsername());
            List<Transaction> transactions = acc.getBettingHistory();
            assertEquals(2, transactions.size());
            checkTransaction("abc123", 150, "Deposit");
            checkTransaction("abc123", 50, "basketball");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
