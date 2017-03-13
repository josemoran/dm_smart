package com.navego360.credito.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.navego360.credito.R;
import com.navego360.credito.interfaces.OfferDialogsListener;

public class  ConfirmDialog extends Dialog {

    private OfferDialogsListener listener;

    public ConfirmDialog(Context context, Fragment fragment){
        super(context);
        listener = (OfferDialogsListener) fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm);
        setUpActions();
    }

    private void setUpActions(){
        TextView acceptBtn = (TextView) findViewById(R.id.accept_btn);
        TextView cancelBtn = (TextView) findViewById(R.id.cancel_btn);

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.generateCredit();
                dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
