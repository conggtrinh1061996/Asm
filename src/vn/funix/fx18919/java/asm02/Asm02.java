package vn.funix.fx18919.java.asm02;

import vn.funix.fx18919.java.asm02.models.Account;
import vn.funix.fx18919.java.asm02.models.Bank;
import vn.funix.fx18919.java.asm02.models.Customer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Asm02 {

    private static final Bank bank = new Bank();

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initUI();
        int optionMenu;
        try {
            optionMenu = sc.nextInt();
            excuteFeature(optionMenu);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            // Hiện lại Menu và yêu cầu nhập lại lựa chọn chức năng
            initUI();
            optionMenu = new Scanner(System.in).nextInt();
            excuteFeature(optionMenu);
        }
    }

    /**
     * Khởi tạo giao diện
     */
    private static void initUI() {
        System.out.println("+----------+-----------------------+------------");
        System.out.println("| NGAN HANG SO | FX18919@v2.0.0                 |");
        System.out.println("+----------+-----------------------+------------");
        System.out.println("1. Them khach hang");
        System.out.println("2. Them tai khoan khach hang");
        System.out.println("3. Hien thi danh sach khach hang");
        System.out.println("4. Tim theo CCCD");
        System.out.println("5. Tim theo ten khach hang");
        System.out.println("0. Thoat");
        System.out.println("+----------+-----------------------+------------");
        System.out.print("Chuc nang: ");
    }

    /**
     * Thực thi tính năng lực chọn từ Menu
     * @param option
     */
    private static void excuteFeature(int option) {
        switch (option) {
            case 1: {
                addNewCustomer();
                break;
            }
            case 2: {
                addNewAccount();
                break;
            }
            case 3: {
                displayCustomerInfo();
                break;
            }
            case 4: {
                System.out.println("Nhap CCCD khach hang: ");
                String cccd = new Scanner(System.in).next();
                if (validCCCD(cccd)) {
                    findByCCCD(cccd);
                } else {
                    System.out.println("Nhap lai CCCD khach hang: ");
                    cccd = new Scanner(System.in).next();
                    if (validCCCD(cccd)) findByCCCD(cccd);
                }
                break;
            }
            case 5: {
                System.out.println("Nhap ten khach hang: ");
                String customerName = new Scanner(System.in).next();
                searchCustomerByName(customerName);
                break;
            }
            case 0: {
                System.exit(0);
                break;
            }
        }
        initUI();
        int newOption = new Scanner(System.in).nextInt();
        excuteFeature(newOption);
    }

    /**
     * Thêm khách hàng mới
     */
    private static void addNewCustomer() {
        System.out.println("Nhap ten khach hang: ");
        String customerName = new Scanner(System.in).next();
        System.out.println("Nhap so CCCD: ");
        //
        String customerId = new Scanner(System.in).next();
        // tạo mới đối tượng customer
        Customer newCustomer = new Customer();
        // đặt giá trị name và id cho customer
        newCustomer.setName(customerName);
        newCustomer.setCustomerId(customerId);
        // lưu thông tin: tên và id của customer vào ngân hàng
        bank.addCustomer(newCustomer);
    }

    /**
     * Kiểm tra căn cước công dân
     * @param cccdCode
     * @return
     */
    private static boolean validCCCD(String cccdCode) {
        Pattern pattern = Pattern.compile("\\d{12}");
        Matcher matcher = pattern.matcher(cccdCode);
        return cccdCode.length() == 12 && matcher.find();
    }

    /**
     * Thêm tài khoản khách hàng
     */
    private static void addNewAccount() {
        System.out.println("Nhap CCCD khach hang: ");
        String customerId = new Scanner(System.in).next();
        if (validCCCD(customerId)) {
            // Thực hiện thêm tài khoản cho khách hàng
            excuteToAddNewAccount(customerId);
        } else {
            System.out.println("Nhap lai CCCD khach hang");
            customerId = new Scanner(System.in).next();
            if (validCCCD(customerId)) {
                excuteToAddNewAccount(customerId);
            }
        }

    }

    /**
     * Thực thi thêm tài khoản khách hàng
     * @param customerId
     */
    private static void excuteToAddNewAccount(String customerId) {
        System.out.println("Nhap ma STK gom 6 chu so: ");
        String accountCode = new Scanner(System.in).next();
        Account newAccount = new Account();
        // kiểm tra mã tài khoản của khách hàng
        if (checkAccountCode(accountCode)) {
            newAccount.setAccountNumber(accountCode);
        }

        System.out.println("Nhap so du: ");
        double balance = new Scanner(System.in).nextDouble();

        // Kiểm tra số dư nhập vào
        if (balance >= 50000) {
            newAccount.setBalance(balance);
        } else {
            System.out.println("So du phai lon hon 50,0000đ. Nhap lai so du: ");
            balance = new Scanner(System.in).nextDouble();
            newAccount.setBalance(balance);
        }
        //
        bank.addAccount(customerId, newAccount);
    }

    /**
     * Kiểm tra mã số tài khoản của khách hàng
     */
    private static boolean checkAccountCode(String accountCode) {
        if (accountCode.length() == 6) {
            return true;
        } else {
            System.out.println("Nhap lai ma so tai khoan: ");
            accountCode = new Scanner(System.in).next();
            checkAccountCode(accountCode);
            return false;
        }
    }

    /**
     * Sinh ra day số có 6 chữ số
     */
    private static String generateRandomNumber() {
        Random random = new Random();
        String numbers = "0123456789";
        StringBuilder sb = new StringBuilder();
        //
        for (int index = 0; index < 6; index++) {
            sb.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return sb.toString();
    }

    /**
     *  Hiển thị thông tin khách hàng
     */
    private static void displayCustomerInfo() {
        for (Customer customer : bank.getCustomers()) {
            customer.disPlayInformation();
        }
    }

    /**
     * Tìm khách hàng theo căn cước công dân
     * @param cccd
     */
    private static void findByCCCD(String cccd) {
        List<String> listCCCCD = new ArrayList<>();
        for (Customer customer : bank.getCustomers()) {
            listCCCCD.add(customer.getCustomerId());
            if (customer.getCustomerId().equals(cccd)) {
                customer.disPlayInformation();
            }
        }
        if (!listCCCCD.contains(cccd)) System.out.println("Khach hang khong ton tai");
    }

    /**
     * Tìm kiếm khách hàng theo tên
     * @param customerName
     */
    private static void searchCustomerByName(String customerName) {
        for (Customer customer : bank.getCustomers()) {
            if (customer.getName().contains(customerName)) {
                customer.disPlayInformation();
            }
        }
    }
}
