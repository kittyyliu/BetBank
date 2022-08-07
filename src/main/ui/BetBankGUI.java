package ui;

import model.Account;
import model.Transaction;
import model.InsufficientFundsException;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.BetBank;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class BetBankGUI extends JFrame implements ActionListener {

    public static final int WIDTH = 700;
    public static final int HEIGHT = 750;
    private static final String JSON_STORE = "./data/account.json";

    private Account account;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private final JTextField amount;
    private JFrame transaction; // frame to add transaction
    private JButton deposit; // button to deposit
    private JButton bet; // button to bet

    private JFrame betType; // frame for choosing sport to bet on
    private JButton basketball;
    private JButton baseball;
    private JButton tennis;
    private JButton hockey;
    private JButton badminton;

    private final DefaultListModel<Transaction> listTransactions;

    private JFrame splashScreen;


    public BetBankGUI() {
        super("BetBank");
        account = new Account("MONEYMAKER222", 100);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        listTransactions = new DefaultListModel<>();
        amount = new JTextField();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLUE);
        splashScreen();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException exception) {
            System.out.println("not working");
        }
        mainPage();
    }

    private void splashScreen() {
        JFrame splashScreen = new JFrame("Loading BetBank");
        splashScreen.setPreferredSize(new Dimension(500, 250));
        splashScreen.setLayout(new BoxLayout(splashScreen.getContentPane(), BoxLayout.Y_AXIS));
        splashScreen.getContentPane().setBackground(Color.WHITE);

        Icon icon = new ImageIcon("./data/loading.gif");
        JLabel gif = new JLabel(icon);
        gif.setBounds(0,0,500,250);
        splashScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        splashScreen.add(gif);
        splashScreen.setVisible(true);
        splashScreen.pack();
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void mainPage() {
        JLabel title = new JLabel("BetBank");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Hind", Font.PLAIN, 40));
        title.setForeground(Color.WHITE);

        JButton transaction = new JButton("Add Transaction");
        transaction.setActionCommand("transaction");
        transaction.addActionListener((ActionListener) this);
        transaction.setAlignmentX(Component.CENTER_ALIGNMENT);
        transaction.setFont(new Font("Hind", Font.PLAIN, 20));
        transaction.setBorder(new LineBorder(Color.WHITE, 1, true));
        transaction.setBackground(new Color(30, 30, 31));
        transaction.setForeground(Color.WHITE);

        JButton balance = new JButton("Check Balance");
        balance.setActionCommand("balance");
        balance.addActionListener((ActionListener) this);
        balance.setAlignmentX(Component.CENTER_ALIGNMENT);
        balance.setFont(new Font("Hind", Font.PLAIN, 20));
        balance.setBorder(new LineBorder(Color.WHITE, 1, true));
        balance.setBackground(new Color(30, 30, 31));
        balance.setForeground(Color.WHITE);

        JButton save = new JButton("Save Account");
        save.setActionCommand("save");
        save.addActionListener((ActionListener) this);
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        save.setFont(new Font("Hind", Font.PLAIN, 20));
        save.setBorder(new LineBorder(Color.WHITE, 1, true));
        save.setBackground(new Color(30, 30, 31));
        save.setForeground(Color.WHITE);

        JButton load = new JButton("Load Account");
        load.setActionCommand("load");
        load.addActionListener((ActionListener) this);
        load.setAlignmentX(Component.CENTER_ALIGNMENT);
        load.setFont(new Font("Hind", Font.PLAIN, 20));
        load.setBorder(new LineBorder(Color.WHITE, 1, true));
        load.setBackground(new Color(30, 30, 31));
        load.setForeground(Color.WHITE);

        JButton exitButton = new JButton("Quit");
        exitButton.setActionCommand("quit");
        exitButton.addActionListener((ActionListener) this);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setFont(new Font("Hind", Font.PLAIN, 20));
        exitButton.setBorder(new LineBorder(Color.WHITE, 1, true));
        exitButton.setBackground(new Color(30, 30, 31));
        exitButton.setForeground(Color.WHITE);


        JLabel moneyImage = new JLabel(new ImageIcon("./data/moneyImage.jpg"));
        moneyImage.setPreferredSize(new Dimension(100, 100));
        moneyImage.setVisible(true);
        moneyImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(title);
        add(moneyImage);
        add(transaction);
        add(balance);
        add(save);
        add(load);
        add(exitButton);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("transaction")) {
            addTransaction();
        } else if (e.getActionCommand().equals("save")) {
            write();
        } else if (e.getActionCommand().equals("load")) {
            read();
        } else if (e.getActionCommand().equals("check balance")) {
            showCurrentBalance();
        } else if (e.getActionCommand().equals("quit")) {
            System.exit(0);
        }
    }

    private void write() {
        try {
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();
            success();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR! Cannot save account.");
        }
    }

    private void read() {
        try {
            account = jsonReader.read();
            addTransaction();
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(null, "ERROR! No saved data was found.");
        }
    }

    // EFFECTS : a fail popup for unsuccessful changes trying to be made
    private void failPage() {
        JFrame failFrame = new JFrame("Fail!");
        failFrame.setPreferredSize(new Dimension(150, 150));
        failFrame.setLayout(new BoxLayout(failFrame.getContentPane(), BoxLayout.Y_AXIS));
        failFrame.getContentPane().setBackground(Color.darkGray);


        JLabel walletImage = new JLabel(new ImageIcon("./data/failImage.png"));
        walletImage.setVisible(true);
        walletImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        failFrame.add(walletImage);
        failFrame.setVisible(true);
        failFrame.pack();
    }

    // EFFECTS : a fail popup for successful changes trying to be made
    private void success() {
        JFrame successFrame = new JFrame("Success!");
        successFrame.setPreferredSize(new Dimension(150, 150));
        successFrame.setLayout(new BoxLayout(successFrame.getContentPane(), BoxLayout.Y_AXIS));
        successFrame.getContentPane().setBackground(Color.darkGray);


        JLabel walletImage = new JLabel(new ImageIcon("./data/successImage.png"));
        walletImage.setVisible(true);
        walletImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        successFrame.add(walletImage);
        successFrame.setVisible(true);
        successFrame.pack();
    }

    private Component addTransaction() {
        JPanel transactionPane = new JPanel();
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
                    failPage();
                }
            } catch (NumberFormatException exception) {
                failPage();
            }
        });
        return depositButton;
    }

    private Component betButton() {
        JButton betButton = new JButton("Bet");
        betButton.setActionCommand("Bet");
        betButton.addActionListener(e -> {
            try {
                if (parseInt(amount.getText()) < 0) {
                    failPage();
                } else {
                    chooseBet();
                }
            } catch (NumberFormatException exception) {
                failPage();
            } //catch (InsufficientFundsException insufficientFundsException) {
                //JOptionPane.showMessageDialog(null,
                        //"ERROR! You have insufficient funds to place this bet");
            //}
        });
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
        success();
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
        success();
    }

    private Component showCurrentBalance() {
        JButton showBalance = new JButton("Check account balance");
        showBalance.setActionCommand("Check account balance");
        JOptionPane.showMessageDialog(null, "Account Balance = $" + account.getBalance());
        return showCurrentBalance();
    }



}
