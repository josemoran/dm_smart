package com.navego360.credito.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.navego360.credito.R;
import com.navego360.credito.interfaces.OfferDialogsListener;
import com.navego360.credito.utils.ValidationsUtils;

public class SendEmailDialog extends Dialog {

    private EditText mailEdtTxt;
    private TextView sendEmailBtn;
    private TextView cancelBtn;

    private OfferDialogsListener listener;

    public SendEmailDialog(Context context, Fragment fragment){
        super(context);
        listener = (OfferDialogsListener) fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_send_email);
        initUi();
        setUpListener();
        setUpActions();
    }

    private void initUi(){
        mailEdtTxt = (EditText) findViewById(R.id.email_field);
        sendEmailBtn = (TextView) findViewById(R.id.accept_btn);
        cancelBtn = (TextView) findViewById(R.id.cancel_btn);
    }

    private void setUpListener(){
        mailEdtTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if(ValidationsUtils.haveEmailFormat(text)) sendEmailBtn.setEnabled(true);
                else sendEmailBtn.setEnabled(false);
            }
        });
    }

    private void setUpActions(){
        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mailEdtTxt.getText().toString();
                listener.sendEmail(email);
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
