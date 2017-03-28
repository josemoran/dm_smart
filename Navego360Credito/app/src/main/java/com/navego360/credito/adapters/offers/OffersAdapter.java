package com.navego360.credito.adapters.offers;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.navego360.credito.R;
import com.navego360.credito.interfaces.OfferItemCheckListener;
import com.navego360.credito.interfaces.OfferItemListener;
import com.navego360.credito.models.credito.Offer;
import com.navego360.credito.widgets.ToastMessage;

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersViewHolder>
        implements OfferItemCheckListener {

    private Activity mActivity;
    private List<Offer> mOffers;
    private OfferItemListener mItemListener;

    private String mMaxAmount = "0";
    private boolean offerTypeCredited = false;

    private int lastPosition;
    private int actualPosition = -1;

    public OffersAdapter(Context context, List<Offer> offers,
                         OfferItemListener itemListener) {
        setList(offers);
        mActivity = (Activity) context;
        mItemListener = itemListener;
    }

    public void replaceData(List<Offer> offers) {
        setList(offers);
        notifyDataSetChanged();
    }

    private void setList(List<Offer> offers) {
        mOffers = offers;
    }

    @Override
    public OffersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.offer_row, parent, false);
        return new OffersViewHolder(view, mItemListener, mMaxAmount, offerTypeCredited, mActivity);
    }

    @Override
    public void onBindViewHolder(OffersViewHolder holder, int position) {
        Offer offer = mOffers.get(position);
        holder.setOfferTypeCredited(offerTypeCredited);
        holder.setOffer(offer);
        holder.setItemCheckListener(this);
    }

    @Override
    public int getItemCount() {
        return mOffers.size();
    }

    @Override
    public void selectOffer(int position, Offer offer) {
        lastPosition = actualPosition;
        actualPosition = position;
        if(lastPosition != actualPosition && lastPosition != -1) {
            Offer offerTmp = mOffers.get(lastPosition);
            offerTmp.setCredited(false);
            mOffers.set(lastPosition, offerTmp);
            notifyItemChanged(lastPosition);
        }
    }

    @Override
    public void invalidSelectOffer() {
        ToastMessage.showMessage(mActivity, mActivity.getString(R.string.invalid_select_offer));
    }

    public void setMaxAmount(String maxAmount){
        mMaxAmount = maxAmount;
        notifyDataSetChanged();
    }

    public void setOfferTypeCredited(boolean credited){
        offerTypeCredited = credited;
        notifyDataSetChanged();
    }
}
