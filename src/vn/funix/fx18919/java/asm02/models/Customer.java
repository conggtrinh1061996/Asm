package vn.funix.fx18919.java.asm02.models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private final List<Account> accounts = new ArrayList<>();

    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Kiểm tra Premium
     */
    public String isPremium() {
        for (Account account : accounts) {
            if (account.isPremium()) {
                return "Premium";
            }
        }
        return "Normal";
    }

    /**
     * Thêm tài khoản
     */
    public void addAccount(Account newAccount) {
        if (accounts.isEmpty()) {
            accounts.add(newAccount);
        } else {
            List<String> accountNumbers = new ArrayList<>();
            for (Account account : accounts) {
                accountNumbers.add(account.getAccountNumber());
            }
            if (!accountNumbers.contains(newAccount.getAccountNumber())) {
                accounts.add(newAccount);
                System.out.println("Them tai khoan thanh cong.");
            } else {
                System.out.println("Tai khoan da ton tai");
            }
        }
    }

    /**
     * Lấy số dư
     */
    public double geSumBalance() {
        double sum = 0;
        for (Account account : accounts) {
            sum += account.getBalance();
        }
        return sum;
    }

    /**
     * Hiển thị thông tin
     */
    public void disPlayInformation() {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        System.out.println(getCustomerId() + "  |           " + getName() + " | " + isPremium() + " |   " + formatter.format(geSumBalance()) + "đ");
        for (int index = 0; index < accounts.size(); index++) {
            System.out.println((index + 1) + "      " + accounts.get(index).toString());
        }
    }
}
