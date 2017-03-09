package com.navego360.credito.utils;

import com.navego360.credito.variables.Constants;

import java.util.Calendar;
import java.util.Date;

public class ExcelUtils {

    public static Date EOMONTH(Date startDate, int month){
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.get(Calendar.DAY_OF_MONTH);
        return c.getTime();
    }

    public static double calculateTea(double tim){
        return Math.pow((1+tim),12) - 1;
    }

    public static double calculateAdjustedCapital(double capital, double tea, int graceDays){
        double adjust = Math.pow((1+tea),((double) graceDays/ Constants.COMMERCIAL_YEAR));
        return adjust*capital;
    }

    public static double calculateQuota(double capital, double tim, double flat, int numQuotas){
        return (((capital*Math.pow((1+tim),numQuotas))*tim)/(Math.pow((1+tim),numQuotas)-1)) +
                (flat*capital)/numQuotas;
    }
}
