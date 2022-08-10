package ui;

import model.exceptions.InsufficientFundsException;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        try {
            new BetBank();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to complete request");
        } catch (InsufficientFundsException e) {
            System.out.println("Unable to complete request");
        }
    }
}
