package vn.funix.fx18919.java.asm03.models;

import vn.funix.fx18919.java.asm02.models.Bank;
import vn.funix.fx18919.java.asm02.models.Customer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigitalBank extends Bank {

    /**
     * Lấy ra thông tin khách hàng
     * @param customerId
     * @return
     */
    public Customer getCustomerById(String customerId) {
        Pattern pattern = Pattern.compile("\\d{12}");
        Matcher matcher = pattern.matcher(customerId);
        Customer customer = new Customer();
        for (Customer c : getCustomers()) {
            // Kiểm tra sự hợp lệ của CustomerId
            if (matcher.find() && isCustomerExisted(customerId)) {
                customer = c;
            }
        }
        return customer;
    }

    /**
     * Thực hiện rút tiền từ tài khoản
     * @param customerId
     * @param accountNumber
     * @param amount
     */
    public void withdraw(String customerId, String accountNumber, double amount) {
        for (Customer customer : getCustomers()) {
            if (customer.getCustomerId().equals(customerId)) {
                ((DigitalCustomer) customer).withdraw(accountNumber, amount);
            }
        }
    }
}
