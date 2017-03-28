package com.navego360.credito.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.navego360.credito.R;
import com.navego360.credito.interfaces.OfferDialogsListener;

public class CreditDialog extends Dialog {

    private OfferDialogsListener listener;

    public CreditDialog(Context context, Fragment fragment){
        super(context);
        listener = (OfferDialogsListener) fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_credited);
        setUpActions();
    }

    private void setUpActions(){
        ImageView closeBtn = (ImageView) findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.closeApp();
            }
        });
    }
}
