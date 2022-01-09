package com.xck.util;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname DateUtil
 * @Description TODO
 * @Date 2020/11/28 8:40
 * @Created by xck503c
 */
public class DateUtil {

    public static final String yyyyMMddhhmmssSSS1 = "yyyy-MM-dd HH:mm:ss,SSS";
    public static final String yyyyMMddhhmmss= "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddhhmm= "yyyy-MM-dd HH:mm";

    public static final String yyyyMMddhhmmssSSS2= "yyyyMMddHHmmssSSS";

    private static Map<String, ThreadLocal<DateFormat>> timeMap = new HashMap<>();

    static {
        timeMap.put(yyyyMMddhhmmssSSS1, ThreadLocal.withInitial(() ->
                new SimpleDateFormat(yyyyMMddhhmmssSSS1)));
        timeMap.put(yyyyMMddhhmmss, ThreadLocal.withInitial(() ->
                new SimpleDateFormat(yyyyMMddhhmmss)));
        timeMap.put(yyyyMMddhhmm, ThreadLocal.withInitial(() ->
                new SimpleDateFormat(yyyyMMddhhmm)));
        timeMap.put(yyyyMMddhhmmssSSS2, ThreadLocal.withInitial(() ->
                new SimpleDateFormat(yyyyMMddhhmmssSSS2)));
    }



    public static String yyyyMMddhhmmssSSSFormat(long time) {
        DateFormat dateFormat = timeMap.get(yyyyMMddhhmmssSSS1).get();
        return dateFormat.format(new Date(time));
    }

    /**
     * 校验格式是否合法
     *
     * @param dateStr
     * @return
     */
    public static boolean isYyyyyMMddhhmm(String dateStr) {
        try {
            if (StringUtils.isEmpty(dateStr)) {
                return false;
            }

            if (dateStr.length() != yyyyMMddhhmm.length()) {
                return false;
            }

            DateFormat dateFormat = timeMap.get(yyyyMMddhhmm).get();

            dateFormat.setLenient(false);
            dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static long diffLongTime(String time1, String time2, String format) {
        if (!timeMap.containsKey(format)) {
            return -1;
        }

        DateFormat dateFormat = timeMap.get(format).get();
        dateFormat.setLenient(false);

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = dateFormat.parse(time2);
            date2 = dateFormat.parse(time1);
        } catch (ParseException e) {
            return -1;
        }

        return date1.getTime() - date2.getTime();
    }

    public static void main(String[] args) {
        System.out.println(isYyyyyMMddhhmm("2021-11-22 33:22"));
        System.out.println(diffLongTime("2021-01-08 22:00", "2021-01-09 00:10", yyyyMMddhhmm)/1000/60);
        System.out.println(diffLongTime("2021-01-09 00:10", "2021-01-09 00:40", yyyyMMddhhmm)/1000/60);
        System.out.println(diffLongTime("2021-01-09 01:00", "2021-01-09 09:18", yyyyMMddhhmm)/1000/60);
        System.out.println(diffLongTime("2021-01-09 10:50", "2021-01-09 12:32", yyyyMMddhhmm)/1000/60);
    }
}
