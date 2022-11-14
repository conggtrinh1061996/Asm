package vn.funix.fx18919.java.asm03.models;

import vn.funix.fx18919.java.asm02.models.Account;
import vn.funix.fx18919.java.asm02.models.Customer;

import static vn.funix.fx18919.java.asm03.Asm03.TYPE_SAVINGS;

public class DigitalCustomer extends Customer {

    /**
     * thực hiện rút tiền
     * @param accountNumber
     * @param amount
     */
    public void withdraw(String accountNumber, double amount) {
        for (Account account : getAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                if (account.getTypeAccount().equals(TYPE_SAVINGS)) {
                    ((SavingsAccount) account).withdraw(amount);
                } else {
                    ((LoansAccount) account).withdraw(amount);
                }
            }
        }
    }
}
