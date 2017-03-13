package com.navego360.credito.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    public static String formatDate1 = "yyyy-MM-dd kk:mm:ss";
    public static String formatDate2 = "dd/MM/yyyy hh:mm a";
    public static String formatDate3 = "dd/MM/yyyy";
    public static String formatDate4 = "dd/MM/yy";
    public static String formatDate5 = "yyyy-MM-dd";

    public static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        String formatted = sdf.format(date);
        return formatted.substring(0, 23) + "Z";
    }

    public static Calendar toCalendar(final String iso8601string) throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string.replace("Z", "-0000");
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(s);
        calendar.setTime(date);
        return calendar;
    }

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

    public static String getLocalTimezone(){
        TimeZone tz = TimeZone.getDefault();
        return tz.getID();
    }

    public static Date convertDate(String dateString, String formatDate) throws ParseException {
        return new SimpleDateFormat(formatDate).parse(dateString);
    }

    public static Date convertDateOtherFormat(Date date, String formatDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        return dateFormat.parse(dateFormat.format(date));
    }

    public static long daysDifference(Date date2, Date date1) throws ParseException {
        long duration  = date2.getTime() - date1.getTime();
        return TimeUnit.MILLISECONDS.toDays(duration);
    }
}
