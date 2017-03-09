package com.navego360.credito.data.offer.remote;

import com.navego360.credito.data.offer.OffersDataSource;
import com.navego360.credito.models.Offer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OffersRemoteDataSource implements OffersDataSource {

    private static OffersRemoteDataSource INSTANCE;

    private static final Map<String, Offer> OFFERS_SERVICE_DATA = new LinkedHashMap<>();

    // Prevent direct instantiation.
    private OffersRemoteDataSource() {}

    public static OffersRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OffersRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getOffers(LoadOffersCallback callback) {
        List<Offer> offers = new ArrayList<>();
        offers.addAll(OFFERS_SERVICE_DATA.values());
        callback.onOffersLoaded(offers);
    }

    @Override
    public void getOffers(String offerTypeId, LoadOffersCallback callback) {
        List<Offer> offers = new ArrayList<>();
        List<Offer> offersByOfferTypeId = new ArrayList<>();
        offers.addAll(OFFERS_SERVICE_DATA.values());
        for (Offer offer : offers){
            if(offer.getOfferTypeId().equals(offerTypeId)) offersByOfferTypeId.add(offer);
        }
        callback.onOffersLoaded(offersByOfferTypeId);
    }

    @Override
    public void getOffer(String offerId, GetOfferCallback callback) {
        Offer offerType = OFFERS_SERVICE_DATA.get(offerId);
        callback.onOfferLoaded(offerType);
    }

    @Override
    public void saveOffer(Offer offer) {
        OFFERS_SERVICE_DATA.put(offer.getId(), offer);
    }

    @Override
    public void creditedOffer(Offer offer) {

    }

    @Override
    public void creditedOffer(String offerId) {

    }

    @Override
    public void refreshOffers() {

    }

    @Override
    public void deleteAllOffers() {
        OFFERS_SERVICE_DATA.clear();
    }

    @Override
    public void deleteOffer(String offerId) {
        OFFERS_SERVICE_DATA.remove(offerId);
    }

    @Override
    public int numOffers() {
        List<Offer> offers = new ArrayList<>();
        offers.addAll(OFFERS_SERVICE_DATA.values());
        return offers.size();
    }
}
