package com.lx.treasure.common.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于处理时间相关
 */
@SuppressWarnings("WeakerAccess")
@Component
public class DateUtils {
    private static final SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat sdfSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /*String 转Date*/
    public static Date stringToDate(String dateStr) throws ParseException {
        if (dateStr.contains(" ") && dateStr.contains(":")) {
            String tmp = dateStr.split(" ")[1];
            String[] dates = tmp.split(":");
            if (dates.length == 2) return sdfMinute.parse(dateStr);
            if (dates.length == 3) return sdfSecond.parse(dateStr);
        }
        return sdfDay.parse(dateStr);
    }

    /*String 转 long*/
    public static Long stringToLong(String dateStr) {
        try {
            return stringToDate(dateStr).getTime();
        } catch (ParseException e) {
            return 0L;
        }
    }

    /**
     *  Date对象格式化为String
     * @param Mode 格式化的字符串
     * @param date 需要格式化的对象
     * @return 格式化好的对象
     */
    public static String dateToString(Date date) {
        return sdfSecond.format(date);
    }

    public static String longToString(long time) {
        return sdfSecond.format(new Date(time));
    }

    public static void main(String[] args) throws ParseException {
        String dateString = "2010-01-01 00:00";
        Date date2000 = stringToDate(dateString);
        System.out.println(date2000);
        System.out.println(date2000.getTime());
        long SYSTEM_START_TIME = 946656000000L;
        Date date = new Date(SYSTEM_START_TIME);
        System.out.println(date);
        long qqq = System.currentTimeMillis() - SYSTEM_START_TIME;
        System.out.println(qqq);
    }
}
