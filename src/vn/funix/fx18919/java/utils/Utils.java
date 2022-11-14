package vn.funix.fx18919.java.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    /**
     * Tạo form mẫu của thời gian
     * @return
     */
    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    /**
     * Lấy mẫu hiển thị đơn vị tiền
     * @param balance
     * @return
     */
    public static String getFormatMoney(double balance) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(balance) + "đ";
    }
}
