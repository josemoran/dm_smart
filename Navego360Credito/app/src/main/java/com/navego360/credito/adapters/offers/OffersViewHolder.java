package com.navego360.credito.adapters.offers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.navego360.credito.R;
import com.navego360.credito.interfaces.OfferItemCheckListener;
import com.navego360.credito.interfaces.OfferItemListener;
import com.navego360.credito.models.credito.Offer;
import com.navego360.credito.utils.AlertDialogUtils;
import com.navego360.credito.utils.DecimalFormatUtils;

public class OffersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context mContext;

    private Offer mOffer;
    private OfferItemListener mItemListener;
    private OfferItemCheckListener mItemCheckListener;

    private View mView;
    private RadioButton mOption;
    private TextView mQuotaView;
    private TextView mCreditDateView;
    private TextView mTceaView;

    private View mOfferInfoView;
    private View mOfferContainerView;
    private TextView mQuotaNotAdjustView;
    private TextView mRateGraceView;
    private TextView mRateProcessView;
    private TextView mMonthGraceView;
    private TextView mDayProcessView;

    private String mMaxAmount;
    private boolean mOfferTypeCredited;

    public OffersViewHolder(View itemView, OfferItemListener listener, String maxAmount,
                            boolean credited, Context context) {
        super(itemView);
        mContext = context;
        mItemListener = listener;
        mMaxAmount = maxAmount;
        mOfferTypeCredited = credited;
        mView = itemView;
        initUI(itemView);
        setUpAction(itemView);
    }

    public void setOffer(Offer offer) {
        mOffer = offer;
        setOfferData();
    }

    public void setOfferTypeCredited(boolean offerTypeCredited){
        mOfferTypeCredited = offerTypeCredited;
    }

    private void initUI(View view){
        mOption = (RadioButton) view.findViewById(R.id.option_btn);
        mQuotaView = (TextView) view.findViewById(R.id.amount_value_view);
        mCreditDateView = (TextView) view.findViewById(R.id.credit_date_value_view);
        mTceaView = (TextView) view.findViewById(R.id.tcea_value_view);
        mOfferInfoView = view.findViewById(R.id.offer_info_detail_view);
        mOfferContainerView = view.findViewById(R.id.container_offer_info_view);
        mQuotaNotAdjustView = (TextView) view.findViewById(R.id.quota_not_adjust_value_view);
        mRateGraceView = (TextView) view.findViewById(R.id.rate_grace_value_view);
        mRateProcessView = (TextView) view.findViewById(R.id.rate_process_value_view);
        mMonthGraceView = (TextView) view.findViewById(R.id.month_grace_value_view);
        mDayProcessView = (TextView) view.findViewById(R.id.process_day_value_view);
    }

    private void setOfferData(){
        if(mOffer.getQuota() != null && !mOffer.getQuota().isEmpty()) {
            String quotaFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(mOffer.getQuota()));
            mQuotaView.setText(quotaFormat);
        } else {
            AlertDialogUtils.createWarningDialog(mContext, R.string.quota_empty_warning);
        }

        if(mOffer.getCreditDate() != null && !mOffer.getCreditDate().isEmpty()) {
            mCreditDateView.setText(mOffer.getCreditDate() + " " + mContext.getString(R.string.date_value));
        } else {
            AlertDialogUtils.createWarningDialog(mContext, R.string.credit_date_empty_warning);
        }

        if(mOffer.getTcea() != null && !mOffer.getTcea().isEmpty()) {
            String tceaFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(mOffer.getTcea()));
            mTceaView.setText(tceaFormat + mContext.getString(R.string.percentage_symbol));
        } else {
            AlertDialogUtils.createWarningDialog(mContext, R.string.tcea_empty_warning);
        }

        if(mOffer.getQuotaNoAdjust() != null && !mOffer.getQuotaNoAdjust().isEmpty()) {
            String quotaNotAdjustFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(mOffer.getQuotaNoAdjust()));
            mQuotaNotAdjustView.setText(quotaNotAdjustFormat);
        } else {
            AlertDialogUtils.createWarningDialog(mContext, R.string.quota_not_adjust_empty_warning);
        }

        if(mOffer.getRateGrace() != null && !mOffer.getRateGrace().isEmpty()) {
            String rateGraceFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(mOffer.getRateGrace()));
            mRateGraceView.setText(rateGraceFormat);
        } else {
            AlertDialogUtils.createWarningDialog(mContext, R.string.rate_grace_empty_warning);
        }

        if(mOffer.getRateProcess() != null && !mOffer.getRateProcess().isEmpty()) {
            String rateProcessFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(mOffer.getRateProcess()));
            mRateProcessView.setText(rateProcessFormat);
        } else {
            AlertDialogUtils.createWarningDialog(mContext, R.string.rate_process_empty_warning);
        }

        if(mOffer.getMonthGrace() != null && !mOffer.getMonthGrace().isEmpty()) {
            mMonthGraceView.setText(mOffer.getMonthGrace());
        } else {
            AlertDialogUtils.createWarningDialog(mContext, R.string.month_grace_empty_warning);
        }

        if(mOffer.getDaysProcess() != null && !mOffer.getDaysProcess().isEmpty()) {
            mDayProcessView.setText(mOffer.getDaysProcess());
        } else {
            AlertDialogUtils.createWarningDialog(mContext, R.string.days_process_empty_warning);
        }

        mOption.setChecked(mOffer.isCredited());

        if(mOffer.isCredited()) mOfferInfoView.setVisibility(View.VISIBLE);
        else mOfferInfoView.setVisibility(View.GONE);

        if(mOfferTypeCredited) {
            Drawable backgroundDisabled;
            if (Build.VERSION.SDK_INT >= 21) {
                backgroundDisabled = ContextCompat.getDrawable(mContext, R.drawable.background_offer_detail_blocked);
            } else {
                backgroundDisabled = mContext.getResources().getDrawable(R.drawable.background_offer_detail_blocked);
            }
            mOfferContainerView.setBackground(backgroundDisabled);
            mOption.setEnabled(false);
            mView.setEnabled(false);
        }
    }

    private void setUpAction(View view){
        mOption.setOnClickListener(this);
        view.setOnClickListener(this);
        mOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) mOfferInfoView.setVisibility(View.GONE);
            }
        });
    }

    public void setItemCheckListener(OffersAdapter adapter){
        mItemCheckListener = adapter;
    }

    @Override
    public void onClick(View v) {
        if(!mOfferTypeCredited) {
            if (Double.parseDouble(mMaxAmount) > Double.parseDouble(mOffer.getQuota())) {
                mOption.setChecked(true);
                mOffer.setCredited(true);
                mOfferInfoView.setVisibility(View.VISIBLE);
                mItemCheckListener.selectOffer(getAdapterPosition(), mOffer);
                mItemListener.onOfferSelected(mOffer);
            } else {
                mOption.setChecked(false);
                mItemCheckListener.invalidSelectOffer();
            }
        }
    }
}
