package com.navego360.credito.utils;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissioUtils {

    ////////////////////////////// PERMISOS /////////////////////////

    // Content Provider
    public static final String READ_PROVIDER = "com.targetmaps.navego360.permRead";
    public static final String WRITE_PROVIDER = "com.targetmaps.navego360.permWrite";
    public static final String[] PROVIDER_PERMS = { READ_PROVIDER, WRITE_PROVIDER };

    public static final int PROVIDER_PERMISSION = 1;

    // Validaciones de permisos para Android M
    public static boolean hasPermission(Context context, String perm) {
        return(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, perm));
    }

    //  Localizacion
    public static boolean canReadProvider(Context context) {
        return(hasPermission(context, READ_PROVIDER));
    }

    public static boolean canWriteProvider(Context context) {
        return(hasPermission(context, WRITE_PROVIDER));
    }

    public static boolean canAccessProvider(Context context) {
        return (canReadProvider(context) && canWriteProvider(context));
    }

    public static void requestPermissionsProvider(Activity activity) {
        ActivityCompat.requestPermissions(activity, PROVIDER_PERMS, PROVIDER_PERMISSION);
    }

}
