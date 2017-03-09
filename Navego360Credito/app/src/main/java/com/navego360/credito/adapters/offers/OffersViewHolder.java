package com.navego360.credito.adapters.offers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.navego360.credito.R;
import com.navego360.credito.interfaces.OfferItemCheckListener;
import com.navego360.credito.interfaces.OfferItemListener;
import com.navego360.credito.models.Offer;
import com.navego360.credito.utils.DecimalFormatUtils;

public class OffersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context mContext;

    private Offer mOffer;
    private OfferItemListener mItemListener;
    private OfferItemCheckListener mItemCheckListener;

    private RadioButton mOption;
    private TextView mQuotaView;
    private TextView mCreditDateView;
    private TextView mTceaView;

    private String mMaxAmount;

    public OffersViewHolder(View itemView, OfferItemListener listener, String maxAmount, Context context) {
        super(itemView);
        mContext = context;
        mItemListener = listener;
        mMaxAmount = maxAmount;
        initUI(itemView);
        setUpAction(itemView);
    }

    public void setOffer(Offer offer) {
        mOffer = offer;
        setOfferData();
    }

    private void initUI(View view){
        mOption = (RadioButton) view.findViewById(R.id.option_btn);
        mQuotaView = (TextView) view.findViewById(R.id.amount_value_view);
        mCreditDateView = (TextView) view.findViewById(R.id.credit_date_value_view);
        mTceaView = (TextView) view.findViewById(R.id.tcea_value_view);
    }

    private void setOfferData(){
        String quotaFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(mOffer.getQuota()));
        mQuotaView.setText(quotaFormat);
        mCreditDateView.setText(mOffer.getCreditDate()  + " " + mContext.getString(R.string.date_value));
        String tceaFormat = DecimalFormatUtils.twoDigitsFormat(Double.valueOf(mOffer.getTcea()));
        mTceaView.setText(tceaFormat + mContext.getString(R.string.percentage_symbol));
        mOption.setChecked(false);
    }

    private void setUpAction(View view){
        mOption.setOnClickListener(this);
        view.setOnClickListener(this);
    }

    public void setItemCheckListener(OffersAdapter adapter){
        mItemCheckListener = adapter;
    }

    @Override
    public void onClick(View v) {
        if(Double.parseDouble(mMaxAmount) > Double.parseDouble(mOffer.getQuota())) {
            mOption.setChecked(true);
            mItemCheckListener.selectOffer(getAdapterPosition(), mOffer);
            mItemListener.onOfferSelected(mOffer);
        } else {
            mOption.setChecked(false);
            mItemCheckListener.invalidSelectOffer();
        }
    }
}
