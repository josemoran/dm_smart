package com.navego360.credito.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Arrays;
import java.util.Date;

import static com.navego360.credito.utils.DateUtils.formatDate6;

public class OfferUtils {
    public static String getCreditDate(){
        Date now = new Date();
        return DateUtils.convertDate(now, formatDate6);
    }

    public static String getDeviceImei(Context context){
        return getUniqueID(context);
    }

    private static String getUniqueID(Context context) {
        String telephonyDeviceId = null;
        String androidDeviceId;

        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            telephonyDeviceId = tm.getDeviceId();
            if (telephonyDeviceId != null) {
                return telephonyDeviceId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (telephonyDeviceId == null) {
            try {
                androidDeviceId = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
                if (androidDeviceId != null) {
                    return getStringIntegerHexBlocks(androidDeviceId.hashCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "0000-0000";
    }

    private static String getStringIntegerHexBlocks(int value) {
        String result = "";
        String string = Integer.toHexString(value);

        int remain = 8 - string.length();
        char[] chars = new char[remain];
        Arrays.fill(chars, '0');
        string = new String(chars) + string;

        int count = 0;
        for (int i = string.length() - 1; i >= 0; i--) {
            count++;
            result = string.substring(i, i + 1) + result;
            if (count == 4) {
                result = "-" + result;
                count = 0;
            }
        }

        if (result.startsWith("-")) {
            result = result.substring(1, result.length());
        }

        return result;
    }
}
