package com.navego360.credito.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    public static String formatDate4 = "dd/MM/yy";
    public static String formatDate5 = "yyyy-MM-dd";
//    public static String formatDate6 = "dd/MM/yyyy HH:mm.ss";
    public static String formatDate6 = "yyyy-MM-dd HH:mm:ss";

    public static String convertDate(String dateString, String formatDate,
                                     String formatConvert) throws ParseException {
        Date date = convertDate(dateString, formatDate);
        SimpleDateFormat sdf = new SimpleDateFormat(formatConvert);
        return sdf.format(date);
    }

    public static String convertDate(Date date, String formatConvert){
        SimpleDateFormat sdf = new SimpleDateFormat(formatConvert);
        return sdf.format(date);
    }

    public static Date convertDate(String dateString, String formatDate) throws ParseException {
        return new SimpleDateFormat(formatDate).parse(dateString);
    }

    public static long daysDifference(Date date2, Date date1) throws ParseException {
        long duration  = date2.getTime() - date1.getTime();
        return TimeUnit.MILLISECONDS.toDays(duration);
    }

    public static Date addDays(String dateString, String formatDate, int addDays) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dateString));
        c.add(Calendar.DATE, addDays);
        return c.getTime();
    }
}
