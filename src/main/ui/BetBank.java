package ui;

import model.Account;
import model.Transaction;
import model.InsufficientFundsException;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class BetBank {
    private Scanner input;
    private Account account;

    public BetBank() throws FileNotFoundException, InsufficientFundsException {
        runBetBank();
    }

    private void runBetBank() throws InsufficientFundsException {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nThank you, goodbye!");
    }

    private void processCommand(String command) {
        if (command.equals("d")) {
            doDeposit();
        } else if (command.equals("b")) {
            doBet();
        }  else if (command.equals("c")) {
            showBalance(account);
        } else if (command.equals("t")) {
            showTransactionHistory(account);
        } else {
            System.out.println("Selection is not valid");
        }
    }

    private void init() {
        account = new Account("MONEYMAKER222", 100);
        input = new Scanner(System.in);
    }

    private void displayMenu() {
        System.out.println("\nHello! Welcome to BetBank :)\nPlease select an option from the following:");
        System.out.println("\nc -> check your current balance");
        System.out.println("\nd -> deposit credits to bet");
        System.out.println("\nb -> bet from account");
        System.out.println("\nt -> show transaction history");
        System.out.println("\nq -> quit\n");
    }

    private void doDeposit() {
        System.out.print("How much would you like to deposit?  $");
        int amount = input.nextInt();

        if (amount >= 0) {
            Transaction b = new Transaction("abc123", amount, "Deposit");
            int credits = amount;
            System.out.println("Your " + credits + " credits have been successfully deposited!\n");
        } else {
            System.out.println("Invalid deposit amount. Please enter a positive number to deposit\n");
        }
    }

    private void doBet() {
        System.out.print("How much would you like to bet?  $");
        int amount = input.nextInt();


        if (amount <= 0) {
            System.out.println("You must bet a positive amount!\n");
        } else if (account.getBalance() < amount) {
            System.out.println("Insufficient account balance. Failed to place bet :(");
            String msg = "Your account balance is currently $";
            String balance = String.valueOf(account.getBalance());
            System.out.println(msg + balance);
        } else {
            displayBetOptions();
            input = new Scanner(System.in);
            String type = input.nextLine();
            Transaction t = new Transaction("abc123", amount, type);
            String msg2 = (t.getBetType());
            String msg3 = String.valueOf((t.getTransactionAmount()));
            System.out.println("Your $" + msg3 + " bet on " + msg2 + " has been successfully placed!\n");
        }
    }

    private void showBalance(Account account) {
        String msg1 = String.valueOf(account.getBalance());
        String msg2 = "Your current account balance is: $";
        System.out.println(msg2 + msg1);
    }

    private void showTransactionHistory(Account account) {
        System.out.println(account.getBettingHistory());
    }

    private void displayBetOptions() {
        System.out.println("\nWhat sport would you like to bet on? \nPlease select an option from the following:");
        System.out.println("\nbasketball");
        System.out.println("\nbaseball");
        System.out.println("\ntennis");
        System.out.println("\nhockey");
        System.out.println("\nbadminton\n");
    }
}
