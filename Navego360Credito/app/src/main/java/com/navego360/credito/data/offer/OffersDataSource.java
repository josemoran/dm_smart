package com.navego360.credito.data.offer;

import com.navego360.credito.models.credito.Offer;

import java.util.List;

public interface OffersDataSource {

    interface LoadOffersCallback {

        void onOffersLoaded(List<Offer> offers);

        void onDataNotAvailable();
    }

    interface GetOfferCallback {

        void onOfferLoaded(Offer offer);

        void onDataNotAvailable();
    }

    void getOffers(LoadOffersCallback callback);

    void getOffers(String offerTypeId, LoadOffersCallback callback);

    void getOffer(String offerId, GetOfferCallback callback);

    void saveOffer(Offer offer);

    void creditedOffer(Offer offer);

    void creditedOffer(String offerId);

    void refreshOffers();

    void deleteAllOffers();

    void deleteOffer(String offerId);

    int numOffers();

}
