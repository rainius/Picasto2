package com.dmtech.app.pcst.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static String getDateTime(long timestamp) {
        Date date = new Date();
        date.setTime(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }
}