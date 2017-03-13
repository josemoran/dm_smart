package com.navego360.credito.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationsUtils {

    public static boolean haveEmailFormat(String text){
        String regEx = "([\\w-]+(?:\\.[\\w-]+)*@([\\w-])+(\\.)+[a-zA-Z]{2,7})";

        Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }
}
