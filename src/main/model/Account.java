package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

// a user's account with their username, balance, account ID, and transactions
// Code referenced from: TellerApp
public class Account {
    private String username;
    private int balance;
    private LinkedList<Transaction> transactions = new LinkedList<>();

    // REQUIRES: name has a non-zero length
    // EFFECTS: name of account is name; if initialBalance >=0 then balance on account is initialBalance,
    // otherwise balance is zero
    public Account(String name, int initialBalance) {
        this.username = name;
        if (initialBalance >= 0) {
            balance = initialBalance;
        } else {
            balance = 0;
        }
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: add given amount to account balance, new balance is returned
    public int deposit(int amount) {
        balance = getBalance() + amount;
        return balance;
    }

    // MODIFIES: this
    // EFFECTS: amount is withdrawn from account and bet on a sport, updated balance is returned
    public int bet(int amount) throws InsufficientFundsException {
        if (amount <= getBalance()) {
            balance = getBalance() - amount;
            return balance;
        } else {
            throw new InsufficientFundsException("You have insufficient funds to place this bet");
        }
    }

    // MODIFIES: this
    // EFFECTS: add a transaction to list of transactions
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // EFFECTS: returns list of transactions previously done
    public LinkedList<Transaction> getBettingHistory() {
        return transactions;
    }

    public String getUsername() {
        return username;
    }

    public int getBalance() {
        return balance;
    }

    // EFFECTS: returns Account as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("balance", balance);
        json.put("transactions", transactionsToJson());
        return json;
    }

    // EFFECTS: returns transactions in account as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t: transactions) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}
