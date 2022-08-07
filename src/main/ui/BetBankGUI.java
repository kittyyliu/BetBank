package ui;

import model.Account;
import model.Transaction;
import model.InsufficientFundsException;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.BetBank;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class BetBankGUI extends JPanel {

    private Account account;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private final JTextField amount;
    private final DefaultListModel<Transaction> listTransactions;

    private static final String JSON_STORE = "./data/account.json";

    public BetBankGUI() {
        super(new BorderLayout());
        account = new Account("MONEYMAKER222", 100);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        listTransactions = new DefaultListModel<>();
        amount = new JTextField(10);

        displaySplashScreen();
        initializeScreen();
    }

    private void displaySplashScreen() {
        JWindow j = new JWindow();

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        Icon img = new ImageIcon(this.getClass().getResource("http://static.demilked.com/wp-content/uploads/2016/06/gif-animations-replace-loading-screen-14.gif"));
        JLabel label = new JLabel(img);
        label.setSize(200, 300);
        j.getContentPane().add(label);
        j.setBounds(((int) d.getWidth() - 722) / 2, ((int) d.getHeight() - 401) / 2, 722, 401);
        j.setVisible(true);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        j.setVisible(false);
    }

    private void initializeScreen() {
        JPanel initialScreen = new JPanel();
        initialScreen.setLayout(new BoxLayout(initialScreen, BoxLayout.PAGE_AXIS));
        JLabel label1 = new JLabel("Hello! Welcome to BetBank! :) \n ");

        initialScreen.add(label1);
        initialScreen.add(Box.createHorizontalStrut(5));
        initialScreen.add(loadAccount());
        initialScreen.add(saveAccount());
        initialScreen.add(createTransaction());
        initialScreen.add(showCurrentBalance());
        //initialScreen.add(showAllTransactions());
        initialScreen.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        add(initialScreen, BorderLayout.CENTER);
    }

    private Component createTransaction() {
        JButton createTransaction = new JButton("Add new transaction");
        createTransaction.setActionCommand("Add new transaction");
        createTransaction.addActionListener(e -> transactionFrame());
        return createTransaction;
    }

    private void transactionFrame() {
        JFrame transactionFrame = new JFrame("New Transaction");
        transactionFrame.getContentPane().add(transactionPane());
        transactionFrame.setSize(500, 200);
        transactionFrame.setVisible(true);
        transactionFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private Component transactionPane() {
        JTextField transactionPane = new JTextField();
        JLabel jlabel = new JLabel("How many credits do you want to deposit or bet from your account?");
        transactionPane.add(jlabel);
        transactionPane.add(Box.createHorizontalStrut(5));
        transactionPane.add(new JSeparator(SwingConstants.VERTICAL));
        transactionPane.add(Box.createHorizontalStrut(5));
        transactionPane.add(amount);
        transactionPane.add(depositButton());
        transactionPane.add(betButton());
        return transactionPane;
    }

    private Component depositButton() {
        JButton depositButton = new JButton("Deposit");
        depositButton.setActionCommand("Deposit");
        depositButton.addActionListener(e -> {
            try {
                int parsedAmount = parseInt(amount.getText());
                if (parsedAmount >= 0) {
                    doDeposit();
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR! You must enter a positive integer.");
                }
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "ERROR! You must enter an integer value.");
            }
        });
        return depositButton;
    }

    private Component betButton() {
        JButton betButton = new JButton("Bet");
        betButton.setActionCommand("Bet");
        betButton.addActionListener(e -> {

        })
        try {
            if (parseInt(amount.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "ERROR! You must enter a positive integer.");
            } else {
                chooseBet();
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "ERROR! You must enter an integer.");
        } catch (InsufficientFundsException insufficientFundsException) {
            JOptionPane.showMessageDialog(null,
                    "ERROR! You have insufficient funds to place this bet");
        }
        return betButton;
    }

    private void chooseBet() {
        JPanel chooseBet = new JPanel();
        chooseBet.setLayout(new BoxLayout(chooseBet, BoxLayout.PAGE_AXIS));
        JLabel label1 = new JLabel("Please choose a sport to bet on from the following: ");

        chooseBet.add(label1);
        chooseBet.add(Box.createHorizontalStrut(5));
        chooseBet.add(sport("Basketball"));
        chooseBet.add(sport("Baseball"));
        chooseBet.add(sport("Tennis"));
        chooseBet.add(sport("Hockey"));
        chooseBet.add(sport("Badminton"));
    }

    private Component sport(String sport) {
        JButton basketball = new JButton(sport);
        basketball.setActionCommand(sport);
        try {
            if (parseInt(amount.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "ERROR! You must enter a positive integer.");
            } else {
                doBet();
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "ERROR! You must enter an integer.");
        } catch (InsufficientFundsException insufficientFundsException) {
            JOptionPane.showMessageDialog(null,
                    "ERROR! You have insufficient funds to place this bet");
        }
        return sport(sport);
    }

    private void doDeposit() {
        int parsedAmount = parseInt(amount.getText());
        Transaction transaction = new Transaction("", 0, "");
        account.deposit(parsedAmount);
        account.addTransaction(transaction);
        transaction.setTransactionAmount(parsedAmount);
        transaction.setTransactionType("Deposit");
        transaction.setTransactionID("Deposit" + Math.round(Math.random() * (9999 - 1000 + 1) + 1));
        listTransactions.addElement(transaction);
        amount.requestFocusInWindow();
        amount.setText("");
    }

    private void doBet() throws InsufficientFundsException {
        int parsedAmount = parseInt(amount.getText());
        Transaction transaction = new Transaction("", 0, "");
        account.bet(parsedAmount);
        account.addTransaction(transaction);
        transaction.setTransactionAmount(parsedAmount);
        transaction.setTransactionType("Bet");
        transaction.setTransactionID("Transaction" + Math.round(Math.random() * (9999 - 1000 + 1) + 1));
        listTransactions.addElement(transaction);
        amount.requestFocusInWindow();
        amount.setText("");
    }

    private Component saveAccount() {
        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(e -> {
            try {
                jsonWriter.open();
                jsonWriter.write(account);
                jsonWriter.close();
                JOptionPane.showMessageDialog(null, "Saved "
                        + account.getUsername() + " to " + JSON_STORE);
            } catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
            }
        });
        return saveButton;
    }


    // MODIFIES: this
    // EFFECTS: loads account from file
    private Component loadAccount() {
        JButton loadButton = new JButton("Load account");
        loadButton.setActionCommand("Load account");
        loadButton.addActionListener(e -> {
            try {
                account = jsonReader.read();
                for (int i = 0; i < account.getBettingHistory().size(); i++) {
                    listTransactions.addElement(account.getBettingHistory().get(i));
                }
                JOptionPane.showMessageDialog(null, "Loaded "
                        + account.getUsername() + " from " + JSON_STORE);
            } catch (IOException exception) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);
            }
        });
        return loadButton;
    }
    
    private Component showCurrentBalance() {
        JButton showBalance = new JButton("Check account balance");
        showBalance.setActionCommand("Check account balance");
        JOptionPane.showMessageDialog(null, "Account Balance = $" + account.getBalance());
        return showCurrentBalance();
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("BetBAnk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new BetBankGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(BetBankGUI::createAndShowGUI);
    }
}
