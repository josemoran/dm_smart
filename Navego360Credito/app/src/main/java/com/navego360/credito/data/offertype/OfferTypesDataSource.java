package com.navego360.credito.data.offertype;

import com.navego360.credito.models.OfferType;

import java.util.List;

public interface OfferTypesDataSource {

    interface LoadOfferTypesCallback {

        void onOfferTypesLoaded(List<OfferType> offerTypes);

        void onDataNotAvailable();
    }

    interface GetOfferTypeCallback {

        void onOfferTypeLoaded(OfferType offerType);

        void onDataNotAvailable();
    }

    void getOfferTypes(LoadOfferTypesCallback callback);

    void getOfferType(String offerTypeId, GetOfferTypeCallback callback);

    void saveOfferType(OfferType offerType);

    void creditedOfferType(OfferType offerType);

    void creditedOfferType(String offerTypeId);

    void refreshOfferTypes();

    void deleteAllOfferTypes();

    void deleteOfferType(String offerTypeId);

    int numOfferTypes();

}