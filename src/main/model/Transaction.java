package model;

import org.json.JSONObject;

// A transaction that contains a transaction ID, a transaction amount, and transaction type
public class Transaction {
    private String transactionID;
    private int transactionAmount;
    private String transactionType;

    // MODIFIES: this
    // EFFECTS: creates new transaction
    public Transaction(String id, int amount, String transaction) {
        transactionID = id;
        transactionAmount = amount;
        transactionType = transaction;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public String getBetType() {
        return transactionType;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("transactionID", transactionID);
        json.put("transactionAmount", transactionAmount);
        json.put("transactionType", transactionType);
        return json;
    }
}
