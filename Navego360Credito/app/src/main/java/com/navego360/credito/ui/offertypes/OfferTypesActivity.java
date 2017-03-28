package com.navego360.credito.ui.offertypes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.navego360.credito.R;
import com.navego360.credito.data.common.local.CreditoRepository;
import com.navego360.credito.utils.ActivityUtils;
import com.navego360.credito.variables.OffersFilterType;
import com.navego360.credito.widgets.ToastMessage;

import static com.navego360.credito.utils.ActivityUtils.closeApp;

public class OfferTypesActivity extends AppCompatActivity {

    private OfferTypesPresenter mOfferTypesPresenter;
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_type);

        try {
            OfferTypesFragment offerTypesFragment = (OfferTypesFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.contentFrame);

            if (offerTypesFragment == null) {
                offerTypesFragment = OfferTypesFragment.newInstance();
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), offerTypesFragment, R.id.contentFrame);
            }

            // Create the presenter
            CreditoRepository repository = CreditoRepository.getInstance(this);

            mOfferTypesPresenter = new OfferTypesPresenter(repository, offerTypesFragment);

            if (savedInstanceState != null) {
                OffersFilterType currentFiltering = (OffersFilterType) savedInstanceState.
                        getSerializable(CURRENT_FILTERING_KEY);
                mOfferTypesPresenter.setFiltering(currentFiltering);
            }
        } catch (Exception e){
            String message = "Message: " + e.getMessage() + "\nLocalized Message" + e.getLocalizedMessage();
            ToastMessage.showMessage(this, message);
            closeApp(this);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, mOfferTypesPresenter.getFiltering());
        super.onSaveInstanceState(outState);
    }

}
