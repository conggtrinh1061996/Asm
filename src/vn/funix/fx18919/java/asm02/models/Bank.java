package vn.funix.fx18919.java.asm02.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {
    private final String id;
    private final List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
        this.id = String.valueOf(UUID.randomUUID());
    }

    public String getId() {
        return id;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Thêm khách hàng mới
     * @param newCustomer
     */
    public void addCustomer(Customer newCustomer) {
        if (!isCustomerExisted(newCustomer.getCustomerId())) {
            customers.add(newCustomer);
        }
    }

    /**
     *  Kiểm tra customer đã tồn tại
     * @param customerId
     * @return
     */
    public boolean isCustomerExisted(String customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) return true;
        }
        return false;
    }

    /**
     * Thêm tài khoản
     * @param customerId
     * @param account
     */
    public void addAccount(String customerId, Account account) {
        for (Customer customer : customers) {
            if (isCustomerExisted(customerId)) {
                customer.addAccount(account);
            }
        }
    }
}
