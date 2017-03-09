package com.navego360.credito.interfaces;

import com.navego360.credito.models.Offer;

public interface OfferItemCheckListener {
    void selectOffer(int position, Offer offer);
    void invalidSelectOffer();
}
