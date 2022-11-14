package vn.funix.fx18919.java.asm02.models;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class User {
    private String name;
    private String customerId;

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        Pattern pattern = Pattern.compile("\\d{12}");
        Matcher matcher = pattern.matcher(customerId);
        if (matcher.find() && customerId.length() == 12) {
            this.customerId = customerId;
//            System.out.println("Nhap ma CCCD thanh cong");
        } else {
            System.out.println("Ma CCCD khong hop le. Yeu cau nhap lai:");
            // Nhap lai cccd
            String cccdCode = new Scanner(System.in).next();
            setCustomerId(cccdCode);
        }
    }
}
