package com.itheima.ssm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class  DateUtils {

    //字符串转日期
    public static Date string2Date(String dateStr,String formatStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = sdf.parse(dateStr);
        return date;
    }

    //日期转字符串
    public static String Date2string(Date date,String formatSting) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatSting);
        String dateString = sdf.format(date);
        return dateString;
    }

}
