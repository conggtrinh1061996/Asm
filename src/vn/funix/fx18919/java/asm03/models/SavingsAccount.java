package vn.funix.fx18919.java.asm03.models;

import jdk.jshell.execution.Util;
import vn.funix.fx18919.java.asm02.models.Account;
import vn.funix.fx18919.java.asm03.interfaces.ReportService;
import vn.funix.fx18919.java.asm03.interfaces.Withdraw;
import vn.funix.fx18919.java.utils.Utils;

public class SavingsAccount extends Account implements ReportService, Withdraw {

    private static final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000;
    private static final double SAVINGS_ACCOUNT_MIN_WITHDRAW = 50000;

    @Override
    public void log(double amount) {
        System.out.println("+----------+--------------------+-----------+");
        System.out.println("        BIEN LAI GIAO DICH SAVINGS");
        System.out.printf("NGAY G/D:                   %s%n", Utils.getDateTime());
        System.out.printf("ATM ID:                     %s%n", "DIGITAL-BANK-ATM 2022");
        System.out.printf("SO TK:                      %s%n", getAccountNumber());
        System.out.printf("SO TIEN:                    %s%n", Utils.getFormatMoney(amount));
        System.out.printf("SO DU:                      %s%n", Utils.getFormatMoney(getBalance()));
        System.out.printf("PHI + VAT:                  %s%n", "0đ");
        System.out.println("+----------+--------------------+-----------+");
    }

    @Override
    public boolean withdraw(double amount) {
        // lưu thông tin giao dịch khách hàng Savings
        Transaction transaction = new Transaction();
        transaction.setTime(Utils.getDateTime());
        if (isAccepted(amount)) {
            System.out.println("Rut tien thanh cong.");
            setBalance(getBalance() - amount);
            log(amount);
            //
            transaction.setAccountNumber(getAccountNumber());
            transaction.setAmount(amount);
            transaction.setStatus(true);
            getTransactions().add(transaction);
            //
            return true;
        }
        getTransactions().add(transaction);
        transaction.setStatus(false);
        return false;
    }

    @Override
    public boolean isAccepted(double amount) {
        if (amount >= SAVINGS_ACCOUNT_MIN_WITHDRAW && amount % 10000 == 0
        && (getBalance() - amount) >= SAVINGS_ACCOUNT_MIN_WITHDRAW) {
            if (!isPremium()) {
                return amount <= SAVINGS_ACCOUNT_MAX_WITHDRAW;
            } else {
                return true;
            }
        }
        System.out.println("So tien nhap khong dung tieu chuan");
        return false;
    }
}
