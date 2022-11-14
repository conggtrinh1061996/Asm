package vn.funix.fx18919.java.asm03.models;

public class Transaction {
    private String id;
    private String accountNumber;
    private double amount;
    private String time;
    private boolean status;

    public void setId(String id) {
        this.id = id;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

    public boolean getStatus() {
        return status;
    }
}
