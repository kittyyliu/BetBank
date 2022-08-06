package ui;

import model.Account;
import model.Transaction;
import model.InsufficientFundsException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static ui.BetBank.JSON_STORE;


public class BetBankGUI extends JPanel {

    private Account account;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private final JTextField amount;

    public BetBankGUI() {
        account = new Account("MONEYMAKER222", 100);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        JFrame frame = new JFrame;
        JPanel panel = new JPanel;
        panel.setBorder(BorderFactory.createEmptyBorder(50,50,20,50));
        panel.setLayout(new GridLayout());

    }

}
