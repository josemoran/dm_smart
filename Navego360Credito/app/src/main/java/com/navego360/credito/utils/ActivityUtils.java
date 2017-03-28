package com.navego360.credito.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.navego360.credito.ui.ExitActivity;

public class ActivityUtils {
    public static void addFragmentToActivity (FragmentManager fragmentManager,
                                              Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    // Cerrar aplicacion
    public static void closeApp(Context mContext){
        ExitActivity.exitApplication(mContext);
    }
}
