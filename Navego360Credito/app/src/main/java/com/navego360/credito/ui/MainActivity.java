package com.navego360.credito.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.navego360.credito.R;
import com.navego360.credito.data.common.local.CreditoRepository;
import com.navego360.credito.ui.offertypes.OfferTypesActivity;
import com.navego360.credito.utils.ActivityUtils;
import com.navego360.credito.utils.BundleUtils;
import com.navego360.credito.widgets.ToastMessage;

import static com.navego360.credito.variables.Constants.SPLASH_SCREEN_DELAY;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private boolean fromNavego = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mContext = this;

        try {
            getSupportActionBar().hide();

            if (getIntent().getExtras() != null) fromNavego = true;
            if (fromNavego) fromNavego = getIntent().getExtras().containsKey("from_navego");
            if (fromNavego) fromNavego = getIntent().getExtras().getBoolean("from_navego");

            if (fromNavego) validateUserDataForm();
            else showCloseAppDialog();
        } catch (Exception e){
            e.printStackTrace();
            String message = "Message: " + e.getMessage() + "\nLocalized Message" + e.getLocalizedMessage();
            ToastMessage.showMessage(mContext, message);
            closeApp();
        }
    }

    private void validateUserDataForm(){
        if(BundleUtils.isUpdatedFormData(mContext, getIntent().getExtras())) showMain();
        else showInvalidUserFormDataDialog();
    }

    private boolean showMain() {
        CreditoRepository repository = CreditoRepository.getInstance(mContext);
        BundleUtils.saveBundle(mContext, getIntent().getExtras(), repository);

        return new Handler().postDelayed(new Runnable() {
            public void run() {
                openHome();
            }
        }, SPLASH_SCREEN_DELAY);
    }

    // Abrir Aplicacion
    private void openHome(){
        Intent home = new Intent().setClass(this, OfferTypesActivity.class);
        startActivity(home);
        finish();
    }

    // Alerta para cerrar aplicacion
    private void showCloseAppDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(getString(R.string.app_name));
        alertDialogBuilder
                .setMessage(getString(R.string.invalid_open_app))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.close_dialog_btn),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        closeApp();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // Cerrar aplicacion
    public void closeApp(){
        ActivityUtils.closeApp(mContext);
    }

    // Alerta en caso no ha editado el formulario de actualizacion de datos
    private void showInvalidUserFormDataDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(getString(R.string.app_name));
        alertDialogBuilder
                .setMessage(getString(R.string.invalid_user_info_updated))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.close_dialog_btn),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        closeApp();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
