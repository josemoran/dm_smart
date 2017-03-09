package com.navego360.credito.data.offertype.remote;

import com.navego360.credito.data.offertype.OfferTypesDataSource;
import com.navego360.credito.models.OfferType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OfferTypesRemoteDataSource implements OfferTypesDataSource {

    private static OfferTypesRemoteDataSource INSTANCE;

    private static final Map<String, OfferType> OFFER_TYPES_SERVICE_DATA = new LinkedHashMap<>();

    // Prevent direct instantiation.
    private OfferTypesRemoteDataSource() {}

    public static OfferTypesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OfferTypesRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getOfferTypes(LoadOfferTypesCallback callback) {
        List<OfferType> offers = new ArrayList<>();
        offers.addAll(OFFER_TYPES_SERVICE_DATA.values());
        callback.onOfferTypesLoaded(offers);
    }

    @Override
    public void getOfferType(String offerTypeId, GetOfferTypeCallback callback) {
        OfferType offerType = OFFER_TYPES_SERVICE_DATA.get(offerTypeId);
        callback.onOfferTypeLoaded(offerType);
    }

    @Override
    public void saveOfferType(OfferType offerType) {
        OFFER_TYPES_SERVICE_DATA.put(offerType.getId(), offerType);
    }

    @Override
    public void creditedOfferType(OfferType offerType) {
        offerType.setCredited(true);
        OFFER_TYPES_SERVICE_DATA.put(offerType.getId(), offerType);
    }

    @Override
    public void creditedOfferType(String offerId) {

    }

    @Override
    public void refreshOfferTypes() {

    }

    @Override
    public void deleteAllOfferTypes() {
        OFFER_TYPES_SERVICE_DATA.clear();
    }

    @Override
    public void deleteOfferType(String offerTypeId) {
        OFFER_TYPES_SERVICE_DATA.remove(offerTypeId);
    }

    @Override
    public int numOfferTypes() {
        List<OfferType> offerTypes = new ArrayList<>();
        offerTypes.addAll(OFFER_TYPES_SERVICE_DATA.values());
        return offerTypes.size();
    }
}
