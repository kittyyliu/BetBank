package model;


public class Transaction {
    private String transactionID;
    private int transactionAmount;
    private String transactionType;

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
}
