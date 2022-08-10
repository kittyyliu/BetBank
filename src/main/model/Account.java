package model;

import model.exceptions.InsufficientFundsException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// a user's account with their username, balance, account ID, and transactions
// Code referenced from: TellerApp
// URL: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
public class Account {
    private String username;
    private int balance;
    private ArrayList<Transaction> transactions = new ArrayList<>();

    // REQUIRES: name has a non-zero length
    // EFFECTS: name of account is name; if initialBalance >=0 then balance on account is initialBalance,
    // otherwise balance is zero
    public Account(String name, int initialBalance) {
        this.username = name;
        this.balance = initialBalance;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: add given amount to account balance, new balance is returned
    public int deposit(int amount) {
        balance = getBalance() + amount;
        EventLog.getInstance().logEvent(new Event(amount + " was deposited successfully"));
        return balance;
    }

    // MODIFIES: this
    // EFFECTS: amount is withdrawn from account and bet on a sport, updated balance is returned
    public int bet(int amount) throws InsufficientFundsException {
        if (amount <= getBalance()) {
            balance = getBalance() - amount;
            EventLog.getInstance().logEvent(new Event(amount + " was bet successfully"));
            return balance;
        } else {
            EventLog.getInstance().logEvent(new Event("Error! Bet of " + amount + " could not be placed"));
            throw new InsufficientFundsException("You have insufficient funds to place this bet");
        }
    }

    // MODIFIES: this
    // EFFECTS: add a transaction to list of transactions
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // EFFECTS: returns list of transactions previously done
    public ArrayList<Transaction> getBettingHistory() {
        return transactions;
    }

    public String getUsername() {
        return username;
    }

    public int getBalance() {
        return balance;
    }

    // code based on JsonSerializationDemo
    // URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns Account as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("balance", balance);
        json.put("transactions", transactionsToJson());
        return json;
    }

    // code based on JsonSerializationDemo
    // URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns transactions in account as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t: transactions) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}
