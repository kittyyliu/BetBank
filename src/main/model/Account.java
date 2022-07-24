package model;

import java.util.LinkedList;

// a user's account with their username, balance, account ID, and transactions
public class Account {
    private String username;
    private int balance;
    private LinkedList<Transaction> transactions = new LinkedList<>();

    public Account(String name, int initialBalance) {
        this.username = name;
        this.balance = initialBalance;
    }

    public int deposit(int amount) {
        balance = getBalance() + amount;
        return balance;
    }

    public int bet(int amount) throws InsufficientFundsException {
        if (amount <= getBalance()) {
            balance = getBalance() - amount;
            return balance;
        } else {
            throw new InsufficientFundsException("You have insufficient funds to place this bet");
        }
    }

    public void addBet(Transaction transaction) {
        transactions.add(transaction);
    }

    public LinkedList<Transaction> getBettingHistory() {
        return transactions;
    }

    public String getUsername() {
        return username;
    }

    public int getBalance() {
        return balance;
    }

}
