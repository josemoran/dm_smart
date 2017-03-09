package com.navego360.credito.utils;

import java.text.DecimalFormat;

public class DecimalFormatUtils {

    public static String numberFormat = "#,##0.00";

    public static String twoDigitsFormat(Double value){
        double finalValue = value >= 0 ? value : value*-1;
        DecimalFormat decimalFormat = new DecimalFormat(numberFormat);
        return decimalFormat.format(finalValue);
    }

    public static String percentageFormat(Double percentage){
        return twoDigitsFormat(percentage) + " %";
    }

    public static String percentageFormat(String percentage){
        return percentageFormat(Double.parseDouble(percentage));
    }
}
