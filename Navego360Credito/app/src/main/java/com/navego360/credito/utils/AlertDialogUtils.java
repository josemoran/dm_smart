package com.navego360.credito.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.navego360.credito.R;

public class AlertDialogUtils {

    public static void createWarningDialog(final Context mContext, int messageStringId){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
        alertDialogBuilder
                .setMessage(mContext.getString(messageStringId))
                .setCancelable(false)
                .setPositiveButton(mContext.getString(R.string.close_dialog_btn),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        ActivityUtils.closeApp(mContext);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
