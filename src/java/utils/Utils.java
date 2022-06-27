/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Elbek
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.TimeZone;

/**
 *
 * @author Elbek
 */
public class Utils {

    private static SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private static void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int high = (b & 0xF0) >> 4;
        int low = b & 0xF;
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    public static String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < block.length; ++i) {
            byte2hex(block[i], buf);
        }
        return buf.toString();
    }

    public static String getStringDigest(String password) {
        String s = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            digest.update(password.getBytes());
            s = toHexString(digest.digest()).toLowerCase();
        } catch (NoSuchAlgorithmException ex) {
//            _logger.error(ex);
        }
        return s;
    }

    public static Boolean parseBoolean(String value) {
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception ex) {
//            _logger.error("value:" + value + " ex:" + ex);
        }
        return null;
    }

    public static Integer parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
//            _logger.error("value:" + value + " ex:" + ex);
        }
        return null;
    }

    public static Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception ex) {
//            _logger.error("value:" + value + " ex:" + ex);
        }
        return null;
    }

    public static Long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception ex) {
//            _logger.error("value:" + value + " ex:" + ex);
        }
        return null;
    }

    public static Float parseFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (Exception ex) {
//            _logger.error("value:" + value + " ex:" + ex);
        }
        return null;
    }

    public static Date parseDate(String value) {
        try {
            return new Date(parseLong(value));
        } catch (Exception ex) {
//            _logger.error("value:" + value + " ex:" + ex);
        }
        return null;
    }

    public static Date parseDate(Long value, String zone) {
        try {
            Calendar calendar = Calendar.getInstance();

            calendar.setTimeZone(TimeZone.getTimeZone(zone));
            calendar.setTimeInMillis(value);
            return calendar.getTime();
        } catch (Exception ex) {
//            _logger.error("value:" + value + " ex:" + ex);
        }
        return null;
    }

    public static Calendar getCalendar() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            return calendar;
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static Date getNextDate(Date date, Integer next) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, next);
            return calendar.getTime();
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static Date getNextMonth(Date date, Integer next) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, next);
            return calendar.getTime();
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static Date getNextYear(Date date, Integer next) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, next);
            return calendar.getTime();
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static String getSDF(Date date) {
        try {
            return SDF.format(date);
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static String getSdf(Date date) {
        try {
            return sdf.format(date);
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static String getDateFormat(Date date, String pattern) {
        try {
            SimpleDateFormat Sdf = new SimpleDateFormat(pattern);
            return Sdf.format(date);
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static Date getDateFormat(String source, String pattern) {
        try {
            SimpleDateFormat Sdf = new SimpleDateFormat(pattern);
            return Sdf.parse(source);
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static String formatDouble(Double value) {
        try {
            return "" + new Formatter().format("%, .2f", value);
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static Double roundDouble(Double value) {
        try {
            return new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        } catch (Exception ex) {
//            _logger.error("value:" + value + " ex:" + ex);
        }
        return value;
    }

    public static Date getStartOfDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 23, 59, 59);
        return calendar.getTime();
    }

    public static Date getStartOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 0, 0, 0);
        return calendar.getTime();
    }

    public static Date getEndOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 23, 59, 59);
        return getNextDate(getNextMonth(calendar.getTime(), 1), -1);
    }

    public static String padLeft(String value, Integer length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(' ');
        }
        return sb.substring(value.length()) + value;
    }

    public static String padLeft(String value, Integer length, Character append) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(append);
        }
        return sb.substring(value.length()) + value;
    }

    public static String padRight(String value, Integer length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(' ');
        }
        return value + sb.substring(value.length());
    }

    public static String padRight(String value, Integer length, Character append) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(append);
        }
        return value + sb.substring(value.length());
    }

    public static String fixPhone(String value) {
        try {
            value = value.replace(" ", "");
            value = value.replace("-", "");
            value = value.replace("+", "");
            value = value.replace("(", "");
            value = value.replace(")", "");
            if (value.length() == 12) {
                value = value.substring(3, 12);
            }
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return value;
    }

    public static Boolean isValidPhoneNumber(String value) {
        Boolean retval = false;
        if (value != null) {
            if (value.length() == 9) {
                int cnt = 0;
                for (int i = 0; i < 9; i++) {
                    if ("0123456789".contains("" + value.charAt(i))) {
                        cnt++;
                    }
                }
                if (cnt == 9) {
                    String oper = value.substring(1, 2);
                    if ("33,77,88,90,91,93,94,95,97,98,99".contains(oper)) {
                        retval = true;
                    }
                }
            }
        }
        return retval;
    }

    public static Boolean isNumber(String value) {
        Boolean retval = false;
        if (value != null) {
            int cnt = 0;
            for (int i = 0; i < value.length(); i++) {
                if ("0123456789".contains("" + value.charAt(i))) {
                    cnt++;
                }
            }
            if (cnt == value.length()) {
                retval = true;
            }
        }
        return retval;
    }

}
