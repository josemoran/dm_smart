package com.navego360.credito.ui.offertypes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.navego360.credito.R;
import com.navego360.credito.adapters.offerstype.OfferTypesAdapter;
import com.navego360.credito.interfaces.OfferTypeItemListener;
import com.navego360.credito.models.credito.OfferType;
import com.navego360.credito.ui.offers.OffersActivity;
import com.navego360.credito.variables.IntentKeys.OfferDetail;
import com.navego360.credito.widgets.ToastMessage;
import com.navego360.credito.widgets.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class OfferTypesFragment extends Fragment implements OfferTypesContract.View, OfferTypeItemListener {

    private OfferTypesContract.Presenter mPresenter;

    private OfferTypesAdapter mListAdapter;

    private TextView mDateOfferView;
    private TextView mNoOffersView;

    private RecyclerView mOffersListView;

    public OfferTypesFragment() {
    }

    public static OfferTypesFragment newInstance() {
        return new OfferTypesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OfferTypeItemListener mItemListener = this;
        mListAdapter = new OfferTypesAdapter(getContext(),new ArrayList<OfferType>(0), mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(OfferTypesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_offers_type, container, false);
        initUI(root);
        return root;
    }

    private void initUI(View root){
        mDateOfferView = (TextView) root.findViewById(R.id.date_offer_view);
        mNoOffersView = (TextView) root.findViewById(R.id.no_offers_view);
        mOffersListView = (RecyclerView) root.findViewById(R.id.offers_list_view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mOffersListView.setLayoutManager(llm);

        int spaceItemsDp = (int) getContext().getResources().getDimension(R.dimen.space_items);
        VerticalSpaceItemDecoration spaceItemDecoration = new VerticalSpaceItemDecoration(spaceItemsDp);
        mOffersListView.addItemDecoration(spaceItemDecoration);

        mOffersListView.setAdapter(mListAdapter);
    }

    @Override
    public void showOfferTypes(List<OfferType> offers, String expirationDate, String creditType) {
        mListAdapter.replaceData(offers);
        mListAdapter.setExpirationDate(expirationDate);
        mListAdapter.setCreditType(creditType);

        mOffersListView.setVisibility(View.VISIBLE);
        mNoOffersView.setVisibility(View.GONE);
    }

    @Override
    public void showClientName(String name) {
        AppCompatActivity activity = ((AppCompatActivity)getContext());
        ViewGroup actionBarLayout = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        TextView bTitle = (TextView) actionBarLayout.findViewById(R.id.action_bar_title);
        bTitle.setText(name.toUpperCase());

        ActionBar actionBar = activity.getSupportActionBar();
        assert actionBar != null;

        actionBar.setCustomView(actionBarLayout);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void showOfferDate(String date) {
        mDateOfferView.setText(date);
    }

    @Override
    public void showNoOfferTypes() {
        showNoOffersViews(getResources().getString(R.string.no_offers_all));
    }

    @Override
    public void showLoadingUserInfoError() {
        showMessage(getString(R.string.loading_user_info_error));
    }

    private void showNoOffersViews(String mainText) {
        mOffersListView.setVisibility(View.GONE);
        mNoOffersView.setVisibility(View.VISIBLE);

        mNoOffersView.setText(mainText);
    }

    @Override
    public void showOfferTypeDetailsUi(String offerId) {
        Intent intent = new Intent(getContext(), OffersActivity.class);
        intent.putExtra(OfferDetail.EXTRA_OFFER_TYPE_ID, offerId);
        startActivity(intent);
    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_offer_message));
    }

    private void showMessage(String message) {
        ToastMessage.showMessage(getContext(), message);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showLoadingOffersError() {
        showMessage(getString(R.string.loading_offes_error));
    }

    @Override
    public void onOfferTypeClick(OfferType offer) {
        if(!offer.isBlocked()) mPresenter.openOfferTypeDetails(offer);
    }
}
