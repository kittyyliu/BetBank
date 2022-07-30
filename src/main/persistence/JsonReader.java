package persistence;

import model.Account;
import model.Transaction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//persistence based on JsonReader from JsonSerializationDemo

public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public Account read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private Account parseAccount(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        int balance = jsonObject.getInt("balance");
        Account acc = new Account(username, balance);
        addTransactions(acc, jsonObject);
        return acc;
    }

    // MODIFIES: acc
    // EFFECTS: parses transactions from JSON object and adds them to account
    private void addTransactions(Account acc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextTransaction = (JSONObject) json;
            addTransaction(acc, nextTransaction);
        }
    }

    // MODIFIES: acc
    // EFFECTS: parses transactions from JSON object and adds it to account
    private void addTransaction(Account acc, JSONObject jsonObject) {
        String transactionID = jsonObject.getString("transactionID");
        int transactionAmount = jsonObject.getInt("transactionAmount");
        String transactionType = jsonObject.getString("transactionType");
        Transaction transaction = new Transaction(transactionID, transactionAmount, transactionType);
        acc.addTransaction(transaction);
    }
}
