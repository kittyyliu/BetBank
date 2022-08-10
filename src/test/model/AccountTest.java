package model;

import model.exceptions.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account a0;
    private Account a1;
    private Account a2;
    private Account a3;
    private Account a4;
    private Account a5;

    @BeforeEach
    void setup() {
        a0 = new Account("seaturtle", -2);
        a1 = new Account("moneymaker222", 0);
        a2 = new Account("ilovebetting3", 1);
        a3 = new Account("itskitliu", 100);
        a4 = new Account("makememoney", 1000);
        a5 = new Account("sugarandspice130", 10000);
    }

    @Test
    void testAccountConstructor() {
        assertEquals(0, a0.getBalance());
        assertEquals("seaturtle", a0.getUsername());
        assertEquals(0, a1.getBalance());
        assertEquals("moneymaker222", a1.getUsername());
        assertEquals(100, a3.getBalance());
        assertEquals("itskitliu", a3.getUsername());
    }


    @Test
    void testOneDeposit() {
        a1.deposit(60);
        a2.deposit(20);
        a3.deposit(30);
        a4.deposit(200);
        a5.deposit(10);
        assertEquals(60, a1.getBalance());
        assertEquals(21, a2.getBalance());
        assertEquals(130, a3.getBalance());
        assertEquals(1200, a4.getBalance());
        assertEquals(10010, a5.getBalance());
    }

    @Test
    void testMultipleDeposits() {
        a2.deposit(20);
        a2.deposit(30);
        a2.deposit(40);
        a2.deposit(50);

        assertEquals(141, a2.getBalance());
    }

    @Test
    void testOneSuccessfulWithdrawal() {
        try {
            a3.bet(20);
        } catch (InsufficientFundsException insufficientFundsException) {
            fail("The withdrawal should go through");
        }
    }

    @Test
    void testMultipleSuccessfulWithdrawals() {
        try {
            a3.bet(10);
            assertEquals(90, a3.getBalance());
            a3.bet(20);
            assertEquals(70, a3.getBalance());
            a3.bet(20);
            assertEquals(50,a3.getBalance());
        } catch(InsufficientFundsException insufficientFundsException) {
            fail("The withdrawals should go through");
        }

    }

    @Test
    void testFailedWithdrawal() {
        try {
            a4.bet(10000);
            fail("Should through exception");
        } catch (InsufficientFundsException insufficientFundsException) {
            assertEquals(1000, a4.getBalance());
        }
    }

    @Test
    void testAddOneBet() {
        Transaction transaction = new Transaction("abc", 10, "basketball bet");
        a2.addTransaction(transaction);
        LinkedList<Transaction> oneTransaction = new LinkedList<>();
        oneTransaction.add(transaction);
        assertEquals(oneTransaction, a2.getBettingHistory());
    }

    @Test
    void testAddMultipleBets() {
        Transaction transaction1 = new Transaction("a", 1, "baseball bet");
        Transaction transaction2 = new Transaction("b", 2, "deposit");
        Transaction transaction3 = new Transaction("c", 3, "hockey bet");
        Transaction transaction4 = new Transaction("d", 4, "tennis bet");
        a5.addTransaction(transaction1);
        a5.addTransaction(transaction2);
        a5.addTransaction(transaction3);
        a5.addTransaction(transaction4);
        LinkedList<Transaction> multipleTransactions = new LinkedList<>();
        multipleTransactions.add(transaction1);
        multipleTransactions.add(transaction2);
        multipleTransactions.add(transaction3);
        multipleTransactions.add(transaction4);
        assertEquals(multipleTransactions, a5.getBettingHistory());
    }


    @Test
    void testGetUsername() {
        assertEquals("moneymaker222", a1.getUsername());
        assertEquals("ilovebetting3", a2.getUsername());
        assertEquals("itskitliu", a3.getUsername());
        assertEquals("makememoney", a4.getUsername());
        assertEquals("sugarandspice130", a5.getUsername());
    }

    @Test
    void testGetBalance() {
        assertEquals(0, a1.getBalance());
        assertEquals(1, a2.getBalance());
        assertEquals(100, a3.getBalance());
        assertEquals(1000, a4.getBalance());
        assertEquals(10000, a5.getBalance());
    }


}