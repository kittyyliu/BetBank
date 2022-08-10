package persistence;

import model.Account;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// code referenced from JsonReaderTest from JsonSerializationDemo
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Account acc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyAccount() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccount.json");
        try {
            Account acc = reader.read();
            assertEquals("MONEYMAKER222", acc.getUsername());
            assertEquals(0, acc.getBettingHistory().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccount.json");
        try {
            Account a = reader.read();
            assertEquals("MONEYMAKER222", a.getUsername());
            List<Transaction> transactions = a.getBettingHistory();
            assertEquals(3, transactions.size());
            checkTransaction("abc123", 300, "Deposit");
            checkTransaction("abc123", 200, "Deposit");
            checkTransaction("abc123", 100, "tennis");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
