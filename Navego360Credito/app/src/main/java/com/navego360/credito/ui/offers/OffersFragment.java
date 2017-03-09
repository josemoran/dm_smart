package com.navego360.credito.ui.offers;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.navego360.credito.R;
import com.navego360.credito.adapters.offers.OffersAdapter;
import com.navego360.credito.dialogs.ConfirmDialog;
import com.navego360.credito.dialogs.CreditDialog;
import com.navego360.credito.interfaces.OfferCreditDialogListener;
import com.navego360.credito.interfaces.OfferItemListener;
import com.navego360.credito.models.Offer;
import com.navego360.credito.utils.DecimalFormatUtils;
import com.navego360.credito.widgets.ToastMessage;

import java.util.ArrayList;
import java.util.List;

import static com.navego360.credito.variables.FragmentKeys.ARGUMENT_OFFER_TYPE_ID;

public class OffersFragment extends Fragment implements OffersContract.View, OfferItemListener, OfferCreditDialogListener{

    private OffersContract.Presenter mPresenter;

    private OffersAdapter mListAdapter;

    private TextView mClientNameView;
    private TextView mMaxAmountView;
    private TextView mCreditTypeView;
    private TextView mBorrowCapacityView;
    private TextView mNoOffersView;
    private TextView mLastAmountView;
    private TextView mRealAmountView;
    private RadioGroup mDisbursementOptions;

    private Button mGenerateBtn;

    private LinearLayout mOfferListContainerView;

    private Offer mOffer;

    private boolean mDisbursementSelected = false;
    private boolean mOfferSelected = false;

    public static OffersFragment newInstance(String offerTypeID) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_OFFER_TYPE_ID, offerTypeID);
        OffersFragment fragment = new OffersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OfferItemListener mItemListener = this;
        mListAdapter = new OffersAdapter(getContext(),new ArrayList<Offer>(0), mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(OffersContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_offers, container, false);
        setHasOptionsMenu(true);
        initUI(root);
        setUpListeners();
        setUpActions();
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                ((Activity)getContext()).finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI(View view){
        mClientNameView = (TextView) view.findViewById(R.id.client_name_view);
        mMaxAmountView = (TextView) view.findViewById(R.id.max_offer_amount_view);
        mCreditTypeView = (TextView) view.findViewById(R.id.credit_type_value_view);
        mBorrowCapacityView = (TextView) view.findViewById(R.id.borrow_value_view);
        mNoOffersView = (TextView) view.findViewById(R.id.no_offers_view);
        mLastAmountView = (TextView) view.findViewById(R.id.last_amount_value_view);
        mRealAmountView = (TextView) view.findViewById(R.id.real_amount_value_view);
        mDisbursementOptions = (RadioGroup) view.findViewById(R.id.disbursement_options);
        mGenerateBtn = (Button) view.findViewById(R.id.generate_btn);
        mOfferListContainerView = (LinearLayout) view.findViewById(R.id.offers_list_container_view);
        RecyclerView mOffersListView = (RecyclerView) view.findViewById(R.id.offers_list_view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mOffersListView.setLayoutManager(llm);
        mOffersListView.setAdapter(mListAdapter);
    }

    private void setUpListeners(){
        mDisbursementOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mDisbursementSelected = true;
                mGenerateBtn.setEnabled(mOfferSelected);
            }
        });
    }

    private void setUpActions(){
        mGenerateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
//                mPresenter.printCreditedOffer(getDisbursementOption(), mOffer);
            }
        });
    }

    @Override
    public void showOffers(List<Offer> offers) {
        mListAdapter.replaceData(offers);

        mOfferListContainerView.setVisibility(View.VISIBLE);
        mNoOffersView.setVisibility(View.GONE);
    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_offer_message));
    }

    @Override
    public void showLoadingOffersError() {
        showMessage(getString(R.string.loading_offes_error));
    }

    @Override
    public void showNoOffer() {
        showNoOffersViews(getResources().getString(R.string.no_offers_all));
    }

    @Override
    public void showMissingOffer() {
        showNoOffersViews(getResources().getString(R.string.no_offers_all));
    }

    @Override
    public void showLoadingUserInfoError() {
        showMessage(getString(R.string.loading_user_info_error));
    }

    @Override
    public void showClientName(String clientName) {
        mClientNameView.setText(clientName.toUpperCase());
    }

    @Override
    public void showAmount(String amount) {
        String amountFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(amount));
        mMaxAmountView.setText(getContext().getString(R.string.money_symbol) +  " " + amountFormat);
    }

    private void showNoOffersViews(String mainText) {
        mOfferListContainerView.setVisibility(View.GONE);
        mNoOffersView.setVisibility(View.VISIBLE);

        mNoOffersView.setText(mainText);
    }

    @Override
    public void showCreditType(String creditType) {
        mCreditTypeView.setText(creditType);
    }

    @Override
    public void showBorrowCapacity(String borrowCapacity) {
        String borrowCapacityFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(borrowCapacity));
        mBorrowCapacityView.setText(getContext().getString(R.string.money_symbol) + " " +  borrowCapacityFormat);
        mListAdapter.setMaxAmount(borrowCapacity);
    }

    @Override
    public void showLastAmount(String lastAmount) {
        String lastAmountFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(lastAmount));
        mLastAmountView.setText(getContext().getString(R.string.money_symbol) + " " +  lastAmountFormat);
    }

    @Override
    public void showRealAmount(String realAmount) {
        String realAmountFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(realAmount));
        mRealAmountView.setText(getContext().getString(R.string.money_symbol) + " " + realAmountFormat);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void showMessage(String message) {
        ToastMessage.showMessage(getContext(), message);
    }

    @Override
    public void onOfferSelected(Offer offer) {
        mOffer = offer;
        mOfferSelected = true;
        mGenerateBtn.setEnabled(mDisbursementSelected);
    }

    private String getDisbursementOption(){
        String disbursementOption;
        int radioButtonID = mDisbursementOptions.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) mDisbursementOptions.findViewById(radioButtonID);
        disbursementOption = radioButton.getText().toString();
        return disbursementOption;
    }

    // Alerta para cerrar aplicacion
    private void showConfirmDialog(){
        ConfirmDialog confirmDialog = new ConfirmDialog(getContext(), this);
        confirmDialog.show();
    }

    @Override
    public void generateCredit() {
        mPresenter.generateCredit();
        CreditDialog dialog = new CreditDialog(getContext());
        dialog.show();
    }
}
