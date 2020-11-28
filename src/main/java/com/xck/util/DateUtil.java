package com.xck.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Classname DateUtil
 * @Description TODO
 * @Date 2020/11/28 8:40
 * @Created by xck503c
 */
public class DateUtil {

    private static SimpleDateFormat yyyyMMddhhmmssSSS = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");

    public static String yyyyMMddhhmmssSSSFormat(long time){
        return yyyyMMddhhmmssSSS.format(new Date(time));
    }
}
