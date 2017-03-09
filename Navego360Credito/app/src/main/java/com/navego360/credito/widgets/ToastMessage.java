package com.navego360.credito.widgets;


import android.content.Context;
import android.widget.Toast;

public class ToastMessage {

    public static void showMessage(Context context, String msj){
        Toast.makeText(context, msj, Toast.LENGTH_LONG).show();
    }

}
