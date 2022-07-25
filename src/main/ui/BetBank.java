package ui;

import model.Account;
import model.Transaction;
import model.InsufficientFundsException;
import java.util.Scanner;
import java.io.FileNotFoundException;

// UI code based on TellerApp

// BetBank application
public class BetBank {
    private Scanner input;
    private Account account;

    // EFFECTS: runs the bank application
    public BetBank() throws FileNotFoundException, InsufficientFundsException {
        runBetBank();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
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

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) throws InsufficientFundsException {
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

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        account = new Account("MONEYMAKER222", 100);
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nHello! Welcome to BetBank :)\nPlease select an option from the following:");
        System.out.println("\nc -> check your current balance");
        System.out.println("\nd -> deposit credits to bet");
        System.out.println("\nb -> bet from account");
        System.out.println("\nt -> show transaction history");
        System.out.println("\nq -> quit\n");
    }

    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void doDeposit() {
        System.out.print("How much would you like to deposit?  $");
        int amount = input.nextInt();

        if (amount >= 0) {
            Transaction b = new Transaction("abc123", amount, "Deposit");
            int credits = amount;
            System.out.println("Your " + credits + " credits have been successfully deposited!\n");
            account.deposit(amount);
            account.addTransaction(b);
            showBalance(account);
        } else {
            System.out.println("Invalid deposit amount. Please enter a positive number to deposit\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts a bet transaction
    private void doBet() throws InsufficientFundsException {
        System.out.print("How much would you like to bet?  $");
        int amount = input.nextInt();

        if (amount <= 0) {
            System.out.println("You must bet a positive amount!\n");
        } else if (account.getBalance() < amount) {
            System.out.println("Insufficient account balance. Unable to place bet :(");
        } else {
            displayBetOptions();
            input = new Scanner(System.in);
            String type = input.nextLine();
            Transaction t = new Transaction("abc123", amount, type);
            if (type.equals("basketball") | type.equals("baseball") | type.equals("tennis") | type.equals("hockey")
                    | type.equals("badminton")) {
                String betType = type;
                String betAmount = String.valueOf(amount);
                System.out.println("Your bet of $" + betAmount + " on " + betType + " has been successfully placed");
                account.bet(amount);
                account.addTransaction(t);
                showBalance(account);
            } else {
                System.out.println("Selection is not valid");
            }
        }
    }

    // EFFECTS: prints account's current balance to the screen
    private void showBalance(Account account) {
        String msg1 = String.valueOf(account.getBalance());
        String msg2 = "Your current account balance is: $";
        System.out.println(msg2 + msg1);
    }

    // EFFECTS: prints account's transaction history to the screen
    private void showTransactionHistory(Account account) {
        System.out.println(account.getBettingHistory());
    }

    // EFFECTS: displays sports bet options to user
    private void displayBetOptions() {
        System.out.println("\nWhat sport would you like to bet on? \nPlease select an option from the following:");
        System.out.println("\nbasketball");
        System.out.println("\nbaseball");
        System.out.println("\ntennis");
        System.out.println("\nhockey");
        System.out.println("\nbadminton\n");
    }
}
