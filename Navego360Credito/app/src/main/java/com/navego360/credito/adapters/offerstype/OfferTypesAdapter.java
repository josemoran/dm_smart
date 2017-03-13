package com.navego360.credito.adapters.offerstype;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.navego360.credito.R;
import com.navego360.credito.interfaces.OfferTypeItemListener;
import com.navego360.credito.models.credito.OfferType;
import com.navego360.credito.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class OfferTypesAdapter extends RecyclerView.Adapter<OfferTypesViewHolder> {

    private Activity mActivity;
    private List<OfferType> mOffersType;
    private OfferTypeItemListener mItemListener;

    private boolean allBlocked = false;

    public OfferTypesAdapter(Context context, List<OfferType> offersType,
                             OfferTypeItemListener itemListener) {
        setList(offersType);
        mActivity = (Activity) context;
        mItemListener = itemListener;
    }

    public void replaceData(List<OfferType> offersType) {
        setList(offersType);
        notifyDataSetChanged();
    }

    public void setExpirationDate(String expirationDate){
        if(expirationDate != null){
            try {
                Date expDate = DateUtils.convertDate(expirationDate, DateUtils.formatDate5);
                Date now = new Date();
                if(expDate.before(now)){
                    allBlocked = true;
                    notifyDataSetChanged();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void setList(List<OfferType> offersType) {
        mOffersType = offersType;
    }

    @Override
    public OfferTypesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.offer_type_row, parent, false);
        return new OfferTypesViewHolder(view, mItemListener, mActivity, allBlocked);
    }

    @Override
    public void onBindViewHolder(OfferTypesViewHolder holder, int position) {
        holder.setOfferType(mOffersType.get(position));
    }

    @Override
    public int getItemCount() {
        return mOffersType.size();
    }
}
