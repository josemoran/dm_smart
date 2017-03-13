package com.navego360.credito.interfaces;

import com.navego360.credito.models.credito.Offer;

public interface OfferItemCheckListener {
    void selectOffer(int position, Offer offer);
    void invalidSelectOffer();
}
