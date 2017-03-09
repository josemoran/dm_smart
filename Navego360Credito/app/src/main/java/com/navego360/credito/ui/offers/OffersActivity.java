package com.navego360.credito.ui.offers;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.navego360.credito.R;
import com.navego360.credito.data.common.local.CreditoRepository;
import com.navego360.credito.data.offer.OffersDataSource;
import com.navego360.credito.data.offer.OffersRepository;
import com.navego360.credito.data.offer.local.OffersLocalDataSource;
import com.navego360.credito.data.offer.remote.OffersRemoteDataSource;
import com.navego360.credito.data.offertype.OfferTypesDataSource;
import com.navego360.credito.data.offertype.OfferTypesRepository;
import com.navego360.credito.data.offertype.local.OfferTypesLocalDataSource;
import com.navego360.credito.data.offertype.remote.OfferTypesRemoteDataSource;
import com.navego360.credito.utils.ActivityUtils;

import static com.navego360.credito.variables.IntentKeys.OfferDetail.EXTRA_OFFER_TYPE_ID;

public class OffersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        String offerTypeId = getIntent().getStringExtra(EXTRA_OFFER_TYPE_ID);

        setUpActionBar();

        OffersFragment offersFragment = (OffersFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (offersFragment == null) {
            offersFragment = OffersFragment.newInstance(offerTypeId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), offersFragment, R.id.contentFrame);
        }

        // Create the presenter
        CreditoRepository repository = CreditoRepository.getInstance(this);
        new OffersPresenter(this, offerTypeId, repository, offersFragment);
    }

    private void setUpActionBar(){
        ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        TextView bTitle = (TextView) actionBarLayout.findViewById(R.id.action_bar_title);
        ImageView bLogo = (ImageView) actionBarLayout.findViewById(R.id.icon);
        bTitle.setText(getResources().getString(R.string.offers_action_bar));
        bLogo.setVisibility(View.GONE);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        actionBar.setCustomView(actionBarLayout);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
