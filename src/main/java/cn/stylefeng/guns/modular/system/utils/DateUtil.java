package cn.stylefeng.guns.modular.system.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static int daysBetween(Date smdate, Date bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    public static String getDefaultDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        return dateFormat.format(date);
    }

    public static String getFormatDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
    public static long getDiffMin(Date startDate, Date endDate) {
        long nm = 1000 * 60;
        long diff = endDate.getTime() - startDate.getTime();
        long min = diff / nm;
        return min;
    }
}
