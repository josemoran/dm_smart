package com.navego360.credito.ui.offers;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.navego360.credito.R;
import com.navego360.credito.adapters.offers.OffersAdapter;
import com.navego360.credito.dialogs.ConfirmDialog;
import com.navego360.credito.dialogs.CreditDialog;
import com.navego360.credito.dialogs.PrintDialog;
import com.navego360.credito.dialogs.SendEmailCompleteDialog;
import com.navego360.credito.dialogs.SendEmailDialog;
import com.navego360.credito.interfaces.OfferDialogsListener;
import com.navego360.credito.interfaces.OfferItemListener;
import com.navego360.credito.models.credito.Offer;
import com.navego360.credito.ui.ExitActivity;
import com.navego360.credito.utils.DecimalFormatUtils;
import com.navego360.credito.widgets.ToastMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.navego360.credito.variables.DisbursementOptions.BOVEDA_OPTION;
import static com.navego360.credito.variables.DisbursementOptions.DEPOSITO_OPTION;
import static com.navego360.credito.variables.DisbursementOptions.GERENCIA_OPTION;
import static com.navego360.credito.variables.FragmentKeys.ARGUMENT_OFFER_TYPE_ID;

public class OffersFragment extends Fragment implements OffersContract.View, OfferItemListener,
        OfferDialogsListener {

    private OffersContract.Presenter mPresenter;

    private OffersAdapter mListAdapter;

    private View mContainerView;

    private TextView mOfferCreditedView;
    private TextView mClientNameView;
    private TextView mMaxAmountView;
    private TextView mCreditTypeView;
    private TextView mApprovedDateView;
    private TextView mTeaView;
    private TextView mFlatView;
    private TextView mDisgraceView;
    private TextView mDiscountView;
    private TextView mBorrowCapacityView;
    private TextView mOfferTitleView;
    private TextView mNoOffersView;
    private TextView mLastAmountView;
    private TextView mRealAmountView;
    private RadioGroup mDisbursementOptions;

    private Button mGenerateBtn;
    private View mCreditedButtonsView;
    private RelativeLayout mPrintBtn;
    private RelativeLayout mSendMailButton;

    private LinearLayout mOfferListContainerView;

    private Offer mOffer;

    private boolean mDisbursementSelected = false;
    private boolean mOfferSelected = false;

    private PrintDialog printDialog;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_offers, container, false);
        initUI(root);
        setHasOptionsMenu(true);
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
        mContainerView = view.findViewById(R.id.offer_view);
        mOfferCreditedView = (TextView) view.findViewById(R.id.offer_credited_view);
        mClientNameView = (TextView) view.findViewById(R.id.client_name_view);
        mMaxAmountView = (TextView) view.findViewById(R.id.max_offer_amount_view);
        mCreditTypeView = (TextView) view.findViewById(R.id.credit_type_value_view);
        mApprovedDateView  = (TextView) view.findViewById(R.id.approved_date_value_view);
        mTeaView = (TextView) view.findViewById(R.id.tea_value_view);
        mFlatView = (TextView) view.findViewById(R.id.flat_value_view);
        mDisgraceView = (TextView) view.findViewById(R.id.disgrace_value_view);
        mDiscountView = (TextView) view.findViewById(R.id.discount_value_view);
        mBorrowCapacityView = (TextView) view.findViewById(R.id.borrow_value_view);
        mOfferTitleView = (TextView) view.findViewById(R.id.offers_label_view);
        mNoOffersView = (TextView) view.findViewById(R.id.no_offers_view);
        mLastAmountView = (TextView) view.findViewById(R.id.last_amount_value_view);
        mRealAmountView = (TextView) view.findViewById(R.id.real_amount_value_view);
        mDisbursementOptions = (RadioGroup) view.findViewById(R.id.disbursement_options);
        mGenerateBtn = (Button) view.findViewById(R.id.generate_btn);
        mCreditedButtonsView = view.findViewById(R.id.credited_buttons_view);
        mPrintBtn = (RelativeLayout) view.findViewById(R.id.print_btn);
        mSendMailButton = (RelativeLayout) view.findViewById(R.id.send_mail_btn);
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
            }
        });
        mPrintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPrintDialog();
//                mPresenter.printCreditedOffer(getDisbursementOption(), mOffer);
            }
        });
        mSendMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSendMailDialog();
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
    public void showApprovedDate(String approvedDate) {
        mApprovedDateView.setText(approvedDate);
    }

    @Override
    public void showTea(String tea) {
        if(tea != null && !tea.equals("")) {
            String teaFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(tea));
            mTeaView.setText(teaFormat + getContext().getString(R.string.percentage_symbol));
        }
    }

    @Override
    public void showFlat(String flat) {
        String flatFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(flat));
        mFlatView.setText(flatFormat + getContext().getString(R.string.percentage_symbol));
    }

    @Override
    public void showDisgrace(String disgrace) {
        String disgraceFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(disgrace));
        mDisgraceView.setText(disgraceFormat + getContext().getString(R.string.percentage_symbol));
    }

    @Override
    public void showDiscount(String discount) {
        mDiscountView.setText(discount);
    }

    @Override
    public void showBorrowCapacity(String borrowCapacity) {
        String borrowCapacityFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(borrowCapacity));
        mBorrowCapacityView.setText(getContext().getString(R.string.money_symbol) + " " +  borrowCapacityFormat);
        mListAdapter.setMaxAmount(borrowCapacity);
    }

    @Override
    public void showLastAmount(String lastAmount) {
        if(lastAmount == null) {
            lastAmount = "0";
        }
        String lastAmountFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(lastAmount));
        mLastAmountView.setText(getContext().getString(R.string.money_symbol) + " " + lastAmountFormat);
    }

    @Override
    public void showRealAmount(String realAmount) {
        String realAmountFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(realAmount));
        mRealAmountView.setText(getContext().getString(R.string.money_symbol) + " " + realAmountFormat);
    }

    @Override
    public void setDisbursementOption(String option) {
        RadioButton optionButton = null;
        if(option != null) {
            if (option.equals(BOVEDA_OPTION)) {
                optionButton = (RadioButton) mDisbursementOptions.findViewById(R.id.checkout_option);
            } else if (option.equals(GERENCIA_OPTION)) {
                optionButton = (RadioButton) mDisbursementOptions.findViewById(R.id.check_option);
            } else if (option.equals(DEPOSITO_OPTION)) {
                optionButton = (RadioButton) mDisbursementOptions.findViewById(R.id.deposit_option);
            }
            if (optionButton != null) {
                if (optionButton.getVisibility() == View.GONE) optionButton = null;
                if (optionButton != null) optionButton.setChecked(true);
            }
        }
    }

    @Override
    public void setDisbursementEnabled(String list) {
        if(list != null) {
            RadioButton optionButton;
            List<String> listEnableOptions = Arrays.asList(list.split(","));
            for (String option : listEnableOptions) {
                if (option.equals(BOVEDA_OPTION)) {
                    optionButton = (RadioButton) mDisbursementOptions.findViewById(R.id.checkout_option);
                    optionButton.setVisibility(View.VISIBLE);
                } else if (option.equals(GERENCIA_OPTION)) {
                    optionButton = (RadioButton) mDisbursementOptions.findViewById(R.id.check_option);
                    optionButton.setVisibility(View.VISIBLE);
                } else if (option.equals(DEPOSITO_OPTION)) {
                    optionButton = (RadioButton) mDisbursementOptions.findViewById(R.id.deposit_option);
                    optionButton.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void showReadOnly() {
        int backgroundDisable, textColorDisable;
        mOfferCreditedView.setVisibility(View.VISIBLE);
        if(Build.VERSION.SDK_INT >= 23) {
            backgroundDisable = ContextCompat.getColor(getContext(), R.color.backgroundColorDisabled1);
            textColorDisable = ContextCompat.getColor(getContext(), R.color.textColor3);
        } else {
            backgroundDisable = getContext().getResources().getColor(R.color.backgroundColorDisabled1);
            textColorDisable = getContext().getResources().getColor(R.color.textColor3);
        }
        mContainerView.setBackgroundColor(backgroundDisable);
        mOfferTitleView.setTextColor(textColorDisable);
        mGenerateBtn.setVisibility(View.GONE);
        mCreditedButtonsView.setVisibility(View.VISIBLE);
        for (int i = 0; i < mDisbursementOptions.getChildCount(); i++) {
            mDisbursementOptions.getChildAt(i).setEnabled(false);
        }
    }

    @Override
    public void blockOfferOptions(boolean blocked) {
        mListAdapter.setOfferTypeCredited(blocked);
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

    private String getDisbursementOptionIndex(){
        String disbursementOption = "0";
        int radioButtonID = mDisbursementOptions.getCheckedRadioButtonId();
        switch (radioButtonID){
            case R.id.checkout_option:
                disbursementOption = BOVEDA_OPTION;
                break;
            case R.id.check_option:
                disbursementOption = GERENCIA_OPTION;
                break;
            case R.id.deposit_option:
                disbursementOption = DEPOSITO_OPTION;
                break;
        }
        return disbursementOption;
    }

    // Dialog para confirmar generar credito
    private void showConfirmDialog(){
        ConfirmDialog confirmDialog = new ConfirmDialog(getContext(), this);
        confirmDialog.show();
    }

    @Override
    public void generateCredit() {
        mPresenter.generateCredit(getDisbursementOptionIndex(), mOffer);
        mPresenter.start();

        CreditDialog dialog = new CreditDialog(getContext(), this);
        dialog.show();
    }

    // Dialog para imprimir
    private void showPrintDialog(){
        printDialog = new PrintDialog(getContext());
        printDialog.show();
    }

    @Override
    public void closePrintDialog() {
        printDialog.dismiss();
    }

    @Override
    public void showCompleteMailDialog() {
        SendEmailCompleteDialog dialog = new SendEmailCompleteDialog(getContext());
        dialog.show();
    }

    // Dialog para enviar correo
    private void showSendMailDialog(){
        SendEmailDialog sendEmailDialog = new SendEmailDialog(getContext(), this);
        sendEmailDialog.show();
    }

    @Override
    public void sendEmail(String email) {
        // mPresenter.sendEmailCredit(getDisbursementOption(), mOffer, email);
    }

    @Override
    public void closeApp() {
        ExitActivity.exitApplication(getContext());
    }

}
