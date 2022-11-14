package vn.funix.fx18919.java.asm03.models;

import vn.funix.fx18919.java.asm02.models.Account;
import vn.funix.fx18919.java.asm03.interfaces.ReportService;
import vn.funix.fx18919.java.asm03.interfaces.Withdraw;
import vn.funix.fx18919.java.utils.Utils;

public class LoansAccount extends Account implements Withdraw, ReportService {

    private static final double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
    private static final double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE  = 0.01;
    private static final double LOAN_ACCOUNT_MAX_BALANCE  = 100000000;

    @Override
    public void log(double amount) {
        System.out.println("+----------+--------------------+-----------+");
        System.out.println("        BIEN LAI GIAO DICH LOAN");
        System.out.printf("NGAY G/D:                   %s%n", Utils.getDateTime());
        System.out.printf("ATM ID:                     %s%n", "DIGITAL-BANK-ATM 2022");
        System.out.printf("SO TK:                      %s%n", getAccountNumber());
        System.out.printf("SO TIEN:                    %s%n", Utils.getFormatMoney(amount));
        System.out.printf("SO DU:                      %s%n", Utils.getFormatMoney(getBalance()));
        System.out.printf("PHI + VAT:                  %s%n", Utils.getFormatMoney(amount * ((isPremium()) ? LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE : LOAN_ACCOUNT_WITHDRAW_FEE)));
        System.out.println("+----------+--------------------+-----------+");
    }

    @Override
    public boolean withdraw(double amount) {
        // lưu thông tin giao dịch khách hàng Loan
        Transaction transaction = new Transaction();
        transaction.setTime(Utils.getDateTime());
        if (isAccepted(amount)) {
            System.out.println("Rut tien thanh cong.");
            setBalance(getBalance() - amount - amount * ((isPremium()) ? LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE : LOAN_ACCOUNT_WITHDRAW_FEE));
            log(amount);
            //
            transaction.setAccountNumber(getAccountNumber());
            transaction.setAmount(amount);
            transaction.setStatus(true);
            getTransactions().add(transaction);
            return true;
        }
        getTransactions().add(transaction);
        transaction.setStatus(false);
        return false;
    }

    @Override
    public boolean isAccepted(double amount) {
        if (amount <= LOAN_ACCOUNT_MAX_BALANCE) {
            double remainMoney = getBalance() - amount - amount * ((isPremium()) ? LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE : LOAN_ACCOUNT_WITHDRAW_FEE);
            return remainMoney >= 50000;
        }
        System.out.println("So tien nhap khong dung tieu chuan");
        return false;
    }
}
