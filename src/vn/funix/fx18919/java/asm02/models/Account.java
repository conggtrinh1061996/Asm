package vn.funix.fx18919.java.asm02.models;

import vn.funix.fx18919.java.asm03.models.Transaction;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private double balance;

    private String typeAccount;

    private final List<Transaction> transactions = new ArrayList<>();

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isPremium() {
        return balance >= 10000000;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return accountNumber + " |           " + typeAccount + " |           " + formatter.format(balance) + "đ";
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
