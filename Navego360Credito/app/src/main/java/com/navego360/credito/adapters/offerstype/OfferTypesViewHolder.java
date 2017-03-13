package com.navego360.credito.adapters.offerstype;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.navego360.credito.R;
import com.navego360.credito.interfaces.OfferTypeItemListener;
import com.navego360.credito.models.credito.OfferType;
import com.navego360.credito.utils.DecimalFormatUtils;
import com.navego360.credito.utils.ViewUtils;
import com.navego360.credito.widgets.ToastMessage;

import static com.navego360.credito.utils.OfferTypeUtils.getOfferTypeColor;
import static com.navego360.credito.utils.OfferTypeUtils.getOfferTypeText;

public class OfferTypesViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;

    private OfferType mOfferType;
    private OfferTypeItemListener mItemListener;

    private View container;
    private View mOfferTypeLogoView;
    private TextView mOfferTypeView;
    private TextView mAmountView;
    private TextView mCreditTypeView;
    private TextView mQuotaView;
    private TextView mCreditDateView;
    private TextView mTceaView;
    private TextView mRateView;
    private TextView mFlatView;
    private TextView mDisgraceView;

    private boolean mAllBlocked;

    public OfferTypesViewHolder(View itemView, OfferTypeItemListener listener, Context context,
                                boolean allBlocked) {
        super(itemView);
        mContext = context;
        mItemListener = listener;
        mAllBlocked = allBlocked;
        container = itemView;
        initUI();
        setUpAction();
    }

    public void setOfferType(OfferType offer) {
        mOfferType = offer;
        setOfferTypeData();
    }

    private void initUI(){
        mOfferTypeLogoView = container.findViewById(R.id.background_offer_type_view);
        mOfferTypeView = (TextView) container.findViewById(R.id.text_offer_type);
        mCreditTypeView = (TextView) container.findViewById(R.id.credit_type_value_view);
        mAmountView = (TextView) container.findViewById(R.id.amount_value_view);
        mQuotaView = (TextView) container.findViewById(R.id.quota_value_view);
        mCreditDateView = (TextView) container.findViewById(R.id.credit_date_value_view);
        mRateView = (TextView) container.findViewById(R.id.rate_value_view);
        mTceaView = (TextView) container.findViewById(R.id.tcea_value_view);
        mFlatView = (TextView) container.findViewById(R.id.flat_value_view);
        mDisgraceView = (TextView) container.findViewById(R.id.disgrace_value_view);
    }

    private void setOfferTypeData(){
        String color = getOfferTypeColor(mContext, mOfferType);
        int logoDrawable = R.drawable.background_offer_type;
        ViewUtils.changeBackground(mContext, mOfferTypeLogoView, color, logoDrawable);
        mOfferTypeView.setText(getOfferTypeText(mContext, mOfferType));
        mOfferTypeView.setTextColor(Color.parseColor(color));

        String amountFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(mOfferType.getAmount()));
        mAmountView.setText(mContext.getString(R.string.money_symbol) + " " + amountFormat);
        mCreditTypeView.setText(mOfferType.getCreditType());

        String quotaFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(mOfferType.getQuota()));
        mQuotaView.setText(mContext.getString(R.string.money_symbol) + " " + quotaFormat);
        mCreditDateView.setText(mOfferType.getCreditDate() + " " + mContext.getString(R.string.date_value));

        String tceaFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(mOfferType.getTcea()));
        mTceaView.setText(tceaFormat + mContext.getString(R.string.percentage_symbol));

        String rateFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(mOfferType.getRate()));
        mRateView.setText(rateFormat + mContext.getString(R.string.percentage_symbol));

        String flatFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(mOfferType.getFlat()));
        mFlatView.setText(flatFormat+ mContext.getString(R.string.percentage_symbol));

        String disgraceFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(mOfferType.getDisgrace()));
        mDisgraceView.setText(disgraceFormat + mContext.getString(R.string.percentage_symbol));

        if(mOfferType.isBlocked()){
            blockValues();
        }
    }

    private void setUpAction(){
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mAllBlocked) mItemListener.onOfferTypeClick(mOfferType);
                else ToastMessage.showMessage(mContext, mContext.getString(R.string.expire_offers_error));
            }
        });
    }

    private void blockValues(){
        int blockedColor = 0;

        if(Build.VERSION.SDK_INT >= 23) {
            ContextCompat.getColor(mContext, R.color.lineColor);
        } else {
            mContext.getResources().getColor(R.color.lineColor);
        }

        TextView mAmountLabelView = (TextView) container.findViewById(R.id.amount_label_view);
        mAmountLabelView.setTextColor(blockedColor);
        mAmountView.setTextColor(blockedColor);
        TextView mCreditTypeLabelView = (TextView) container.findViewById(R.id.credit_type_label_view);
        mCreditTypeLabelView.setTextColor(blockedColor);
        mCreditTypeView.setTextColor(blockedColor);
        TextView mQuotaLabelView = (TextView) container.findViewById(R.id.quota_label_view);
        mQuotaLabelView.setTextColor(blockedColor);
        mQuotaView.setTextColor(blockedColor);
        TextView mCreditDateLabelView = (TextView) container.findViewById(R.id.credit_date_label_view);
        mCreditDateLabelView.setTextColor(blockedColor);
        mCreditDateView.setTextColor(blockedColor);
        TextView mTceaLabelView = (TextView) container.findViewById(R.id.tcea_label_view);
        mTceaLabelView.setTextColor(blockedColor);
        mTceaView.setTextColor(blockedColor);
        TextView mFlatLabelView = (TextView) container.findViewById(R.id.flat_label_view);
        mFlatLabelView.setTextColor(blockedColor);
        mFlatView.setTextColor(blockedColor);
        TextView mRateLabelView = (TextView) container.findViewById(R.id.rate_label_view);
        mRateLabelView.setTextColor(blockedColor);
        mRateView.setTextColor(blockedColor);
        TextView mDisgraceLabelView = (TextView) container.findViewById(R.id.disgrace_label_view);
        mDisgraceLabelView.setTextColor(blockedColor);
        mDisgraceView.setTextColor(blockedColor);
    }

}
