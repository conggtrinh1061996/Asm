package vn.funix.fx18919.java.asm03;

import jdk.jshell.execution.Util;
import vn.funix.fx18919.java.asm02.models.Account;
import vn.funix.fx18919.java.asm02.models.Customer;
import vn.funix.fx18919.java.asm03.models.*;
import vn.funix.fx18919.java.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Asm03 {

    private static final int EXIT_COMMAND_CODE = 0;
    private static final int EXIT_ERROR_CODE = 1;
    private static final DigitalBank activeBank = new DigitalBank();
    private static final String CUSTOMER_ID = "001215000001";
    private static final String CUSTOMER_NAME = "FUNIX";
    private static final Scanner sc = new Scanner(System.in);
    public static final String TYPE_SAVINGS = "SAVINGS";
    public static final String TYPE_LOAN = "LOAN";

    public static void main(String[] args) {
        // Khởi tạo và gán name và id cho customer
        Customer newCustomer = new DigitalCustomer();
        newCustomer.setName(CUSTOMER_NAME);
        newCustomer.setCustomerId(CUSTOMER_ID);
        activeBank.addCustomer(newCustomer);
        // Hiển thị giao diện người dùng
        initUI();
        int optionMenu;
        try {
            // Nhập sự lựa chọn khách hàng
            optionMenu = new Scanner(System.in).nextInt();
            excuteOptionMenu(optionMenu);
        } catch (Exception e) {
            System.out.println(e);
            // Hiện lại Menu và yêu cầu nhập lại lựa chọn chức năng
            initUI();
            optionMenu = new Scanner(System.in).nextInt();
            excuteOptionMenu(optionMenu);
        }
    }

    /**
     * Khởi tạo Menu
     */
    private static void initUI() {
        System.out.println("+----------+----------------------+-----------+");
        System.out.println("| NGAN HANG SO | FX18919@v3.0.0               |");
        System.out.println("+----------+----------------------+-----------+");
        System.out.println("1. Thong tin khach hang");
        System.out.println("2. Them tai khoan ATM");
        System.out.println("3. Them tai khoan tin dung");
        System.out.println("4. Rut tien");
        System.out.println("5. Lich su giao dich");
        System.out.println("0. Thoat");
        System.out.println("+----------+----------------------+-----------+");
        System.out.print("Chuc nang: ");
    }

    /**
     * Hàm thực thi các tính năng khi user lựa chọn
     * @param optionMenu
     */
    private static void excuteOptionMenu(int optionMenu) {
        switch (optionMenu) {
            case 1: {
                showCustomer();
                break;
            }

            case 2: {
                addNewAccount(TYPE_SAVINGS);
                break;
            }

            case 3: {
                addNewAccount(TYPE_LOAN);
                break;
            }

            case 4: {
                withdrawMoney();
                break;
            }

            case 5: {
                showHistoryTrade();
                break;
            }

            case 0: {
                System.exit(EXIT_COMMAND_CODE);
                break;
            }

            default: {
                System.out.println("Chuc nang nhap vao khong dung. Vui long nhap lai:");
            }
        }
        initUI();
        int newOptionMenu = new Scanner(System.in).nextInt();
        excuteOptionMenu(newOptionMenu);
    }

    /**
     * Hiện thông tin khách hàng
     */
    private static void showCustomer() {
        Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
        if (customer != null) customer.disPlayInformation();
    }

    /**
     * Thêm tài khoản Savings hoặc Loan
     * @param typeAccount
     */
    private static void addNewAccount(String typeAccount) {
        System.out.println("Nhap ma so tai khoan gom 6 chu so:");
        String accountCode = sc.next();
        while (!checkAccountCode(accountCode)) {
            System.out.println("Ma so tai khoan khong hop le. Nhap lai ma so tai khoan gom 6 chu so:");
            accountCode = sc.next();
        }
        System.out.println("Nhap so du ban dau: ");
        double originalBalance = sc.nextDouble();
        // tạo ra tài khoản mới
        Account account = (typeAccount.equals(TYPE_SAVINGS)) ? new SavingsAccount() : new LoansAccount();
        account.setAccountNumber(accountCode);
        account.setBalance(originalBalance);
        account.setTypeAccount(typeAccount);

        activeBank.addAccount(CUSTOMER_ID, account);
    }

    /**
     * Tạo ra 6 chữ số ngẫu nhiên tài khoản khách hàng
     * @return
     */
    private static String getNumberAccount() {
        Random random = new Random();
        String numbers = "0123456789";
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < 6; index++) {
            sb.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return sb.toString();
    }

    /**
     * Kiểm tra mã số tài khoản
     * @param accountCode
     * @return
     */
    private static boolean checkAccountCode(String accountCode) {
        Pattern pattern = Pattern.compile("\\d{6}");
        Matcher matcher = pattern.matcher(accountCode);
        return accountCode.length() == 6 && matcher.find();
    }

    /**
     * Thực thi tính năng rút tiền khỏi tài khoản
     */
    private static void withdrawMoney() {
        System.out.println("Nhap tai khoan can rut: ");
        String accountCode = sc.next();
        double withdrawMoney;
        // Xác thực tài khoản của khách hàng
        if (validAccount(accountCode)) {
            System.out.println("Nhap so tien can rut: ");
            withdrawMoney = sc.nextDouble();
            activeBank.withdraw(CUSTOMER_ID, accountCode, withdrawMoney);
        } else {
            // Xử lí khi khách hàng nhập sai số tài khoản
            do {
                System.out.printf("Tai khoan %s khong ton tai. Moi nhap lai:%n", accountCode);
                accountCode = sc.next();
            } while (!validAccount(accountCode));
            System.out.println("Nhap so tien can rut: ");
            withdrawMoney = sc.nextDouble();
            activeBank.withdraw(CUSTOMER_ID, accountCode, withdrawMoney);
        }

    }

    /**
     * Kiểm tra sự hợp lệ của tài khoản
     * @param account
     * @return
     */
    private static boolean validAccount(String account) {
        List<String> accounts = new ArrayList<>();
        for (Customer customer : activeBank.getCustomers()) {
            for (Account acc : customer.getAccounts()) {
                accounts.add(acc.getAccountNumber());
            }
        }
        //
        Pattern pattern = Pattern.compile("\\d{6}");
        Matcher matcher = pattern.matcher(account);
        if (account.length() == 6 && matcher.find() && accounts.contains(account)) {
            System.out.printf("Tai khoan %s hop le.%n", account);
            return true;
        }
        return false;
    }

    /**
     * Hiển thị lịch sử giao dịch
     */
    private static void showHistoryTrade() {
        System.out.println("+----------+----------------------+-----------+");
        System.out.println("| LICH SU GIAO DICH                           |");
        System.out.println("+----------+----------------------+-----------+");
        for (Customer customer : activeBank.getCustomers()) {
            System.out.printf("%s |             %s |    %s |    %s%n", CUSTOMER_ID, CUSTOMER_NAME, customer.isPremium(), Utils.getFormatMoney(customer.geSumBalance()));
            for (int index = 0; index < customer.getAccounts().size(); index++) {
                Account account = customer.getAccounts().get(index);
                System.out.printf("%s     %s |             %s |              %s%n", (index + 1), account.getAccountNumber(), account.getTypeAccount(), Utils.getFormatMoney(account.getBalance()));
            }

            for (Account account : customer.getAccounts()) {
                for (Transaction transaction : account.getTransactions()) {
                    if (transaction.getStatus()) {
                        System.out.printf("%s |     %s | %s%n", transaction.getAccountNumber(), Utils.getFormatMoney(-transaction.getAmount()), transaction.getTime());
                    }
                }
            }
        }
        System.out.println("+----------+----------------------+-----------+");
    }

}
