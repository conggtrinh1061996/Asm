package vn.funix.fx18919.java.asm01;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Asm01 {

    static int n;
    static boolean isRunAgain = false;

    public static void main(String[] args) {
        setUpAction();
    }

    private static void setUpAction() {
        setUpUI();
        try {
            // Nhap n tu ban phim nguoi dung
            System.out.print("Chuc nang: ");
            n = new Scanner(System.in).nextInt();
            System.out.println();
            // Kiem tra n nhap vao tu ban phim
            if (n == 1) {
                if (checkVerifyCode()) {
                    // Xu li sau khi xac nhan thanh cong
                    showDataPerson();
                }
            } else if (n == 0) {
                System.out.println("+----------+--------------------+----------+");
                System.exit(n);
            }

        } catch (Exception e) {
            isRunAgain = true;
            runAgain();
        }
    }

    /**
     * Chạy hiển thị lại khi user nhập sai
     */
    private static void runAgain() {
        // Xu li khi user nhap khong dung yeu cau
        int againNumber = 0;
        do {
            System.out.println("Yeu cau nhap lai");
            try {
                againNumber = new Scanner(System.in).nextInt();
                if (againNumber == 1 && isRunAgain) {
                    if (checkVerifyCode()) {
                        // Xu li sau khi xac nhan thanh cong
                        showDataPerson();
                    }
                } else if (againNumber == 0) {
                    System.out.println("+----------+--------------------+----------+");
                    System.exit(0);
                }
            } catch (Exception e) {
                runAgain();
            }
        } while (againNumber != 1);
    }

    /**
     * Hiển thị Menu
     */
    public static void setUpUI() {
        System.out.println("+----------+--------------------+----------+");
        System.out.println("| NGAN HANG SO | FX18919@v1.0.0            |");
        System.out.println("+----------+--------------------+----------+");
        System.out.println("| 1. Nhap CCCD                             |");
        System.out.println("| 0. Thoat                                 |");
        System.out.println("+----------+--------------------+----------+");
    }

    /**
     * Tạo mã xác nhận 6 kí tự HARD
     * @return
     */
    public static String verifyCode() {
        Random random = new Random();
        String upperString = "ABCEFGHIJKLMNOPWXYZ";
        String lowerString = upperString.toLowerCase();
        String numbers = "0123456789";

        String verifyCode = upperString + lowerString + numbers;
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < 6; index++) {
            sb.append(verifyCode.charAt(random.nextInt(verifyCode.length())));
        }
        return sb.toString();
    }

    /**
     * Kiểm tra mã xác nhận
     * @return
     */
    public static boolean checkVerifyCode() {
        System.out.println("VerifyCode:");
        System.out.println("1.EASY");
        System.out.println("2.HARD");
        int optionVerifyCode;
        try {
            optionVerifyCode = new Scanner(System.in).nextInt();
            String verifyCode = (optionVerifyCode == 1) ? String.valueOf(generateVerifyNumberCode()) : generateVerifyCode();
            //
            System.out.println("Ma xac nhan: " + verifyCode);
            String checkVerifyCode = new Scanner(System.in).next();
            if (checkVerifyCode.equals(verifyCode)) {
                // code sau khi da verify code thanh cong
                System.out.println("Ma xac nhan chinh xac");
                return true;
            } else {
                System.out.println("Ma xac thuc khong dung. Vui long thu lai");
                // tao lai ma xac thuc
                String newVerifyCode = (optionVerifyCode == 1) ? String.valueOf(generateVerifyNumberCode()) : generateVerifyCode();
                checkVerifyCode = new Scanner(System.in).next();
                if (checkVerifyCode.equals(newVerifyCode)) {
                    // code sau khi da verify code thanh cong
                    System.out.println("Ma xac nhan chinh xac");
                    return true;
                } else {
                    System.exit(0);
                    return false;
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * Tạo ra mã xác nhận với cả số và chữ số 6 kí tự
     * @return
     */
    private static String generateVerifyCode() {
        // Lay ma xac nhan
        String verifyCode = verifyCode();
        System.out.println("Nhap ma xac thuc: " + verifyCode);
        return verifyCode;
    }

    /**
     * Tạo ra mã xác nhận là số từ 100 đến 999
     * @return
     */
    private static int generateVerifyNumberCode() {
        Random random = new Random();
        return  random.nextInt(888) + 100;
    }

    /**
     * Hiển thị thông tin người được chọn
     */
    private static void showDataPerson() {
        System.out.print("Vui long nhap so CCCD: ");
        String CCCDCode = new Scanner(System.in).next();
        if (validCCCDCode(CCCDCode)) {
            showOptionsMenu();
            initDataPerson(CCCDCode);
        } else {
            System.out.println("So CCCD khong hop le. Vui long nhap lai hoac 'No' de thoat");
            String textNo = new Scanner(System.in).next();
            if (textNo.equals("No")) {
                System.exit(0);
            } else {
                showOptionsMenu();
                initDataPerson(textNo);
            }
        }
    }

    /**
     * Khởi tạo thông tin người được chọn
     * @param cccdCode
     */
    private static void initDataPerson(String cccdCode) {
        Pattern pattern = Pattern.compile("(^\\d{3})(\\d)(\\d{2})(\\d+)");
        Matcher matcher = pattern.matcher(cccdCode);
        String gender, birthYear, place, randomNumber;
        while (matcher.find()) {
            gender = getGender(matcher.group(2));
            birthYear = getBirthYear(matcher.group(2), matcher.group(3));
            place = getProvince(matcher.group(1));
            randomNumber = matcher.group(4);
            showInfoPerson(gender, birthYear, place, randomNumber);
        }
    }

    /**
     * Hiển thị lựa chọn menu
     */
    private static void showOptionsMenu() {
        System.out.println("\t| 1. Kiem tra noi sinh");
        System.out.println("\t| 2. Kiem tra tuoi, gioi tinh");
        System.out.println("\t| 3. Kiem tra so ngau nhien");
        System.out.println("\t| 0. Thoat");
    }

    /**
     * Hiển thị dữ liệu người dùng tương ứng
     * @param gender
     * @param birthYear
     * @param place
     * @param randomNumber
     */
    private static void showInfoPerson(String gender, String birthYear, String place, String randomNumber) {
        System.out.print("Chuc nang: ");
        int featureNumber = new Scanner(System.in).nextInt();
        // tạo biến thoát vofng lặp
        boolean isBreakLoop = false;
        while (!isBreakLoop){
            System.out.println();
            switch (featureNumber) {
                case 1: {
                    System.out.println("Noi sinh: " + place);
                    showOptionsMenu();
                    System.out.print("Chuc nang: ");
                    featureNumber = new Scanner(System.in).nextInt();
                    break;
                }
                case 2: {
                    System.out.println("Gioi tinh: " + gender + " | " + birthYear);
                    showOptionsMenu();
                    System.out.print("Chuc nang: ");
                    featureNumber = new Scanner(System.in).nextInt();
                    break;
                }
                case 3: {
                    System.out.println("So ngau nhien: " + randomNumber);
                    showOptionsMenu();
                    System.out.print("Chuc nang: ");
                    featureNumber = new Scanner(System.in).nextInt();
                    break;
                }
                case 0: {
                    setUpUI();
                    try {
                        // Nhap n tu ban phim nguoi dung
                        System.out.print("Chuc nang: ");
                        n = new Scanner(System.in).nextInt();
                        System.out.println();
                        // Kiem tra n nhap vao tu ban phim
                        if (n == 1) {
                            if (checkVerifyCode()) {
                                // Xu li sau khi xac nhan thanh cong
                                showDataPerson();
                            }
                        } else if (n == 0) {
                            System.out.println("+----------+--------------------+----------+");
                            System.exit(n);
                        }

                    } catch (Exception e) {
                        isRunAgain = true;
                        runAgain();
                    }
                    isBreakLoop = true;
                }

               /* default: {
                    System.out.println("Yeu cau nhap lai");
                    showOptionsMenu();
                    System.out.print("Chuc nang: ");
                    break;
                }*/
            }
        }
    }

    /**
     *  Lấy thông tin tỉnh thành
     * @param provinceCode
     * @return
     */
    private static String getProvince(String provinceCode) {
        switch (provinceCode) {
            case "001": {
                return "Ha Noi";
            }
            case "002": {
                return "Ha Giang";
            }
            case "037": {
                return "Ninh Binh";
            }
            case "048": {
                return "Da Nang";
            }
            case "079": {
                return "Ho Chi Minh";
            }
            default: return "";
        }
    }

    /**
     *  Lấy thông tin giới tính
     * @param genderCode
     * @return
     */
    private static String getGender(String genderCode) {
        return (Integer.parseInt(genderCode) % 2 == 0) ? "Nam" : "Nu";
    }

    /**
     *  Lấy thông tin năm sinh
     * @param genderCode
     * @param lastBirthYear
     * @return
     */
    private static String getBirthYear(String genderCode, String lastBirthYear) {
        int gender = Integer.parseInt(genderCode);
        if (gender == 0 || gender == 1) {
            return "19" + lastBirthYear;
        } else {
            return "20" + lastBirthYear;
        }
    }

    /**
     * Xác nhận mã CCCD
     * @param cccdCode
     * @return
     */
    private static boolean validCCCDCode(String cccdCode) {
        Pattern pattern = Pattern.compile("\\D");
        Matcher matcher = pattern.matcher(cccdCode);
        return cccdCode.length() == 12 && !matcher.find();
    }
}
