package com.navego360.credito.ui.offertypes;

import com.navego360.credito.models.credito.OfferType;
import com.navego360.credito.ui.common.BasePresenter;
import com.navego360.credito.ui.common.BaseView;
import com.navego360.credito.variables.OffersFilterType;

import java.util.List;

public interface OfferTypesContract {

    interface View extends BaseView<Presenter> {
        void showOfferTypes(List<OfferType> offerTypes, String expirationDate, String creditType);
        void showClientName(String name);
        void showOfferDate(String date);
        void showOfferTypeDetailsUi(String offerId);
        void showSuccessfullySavedMessage();
        void showLoadingOffersError();
        void showNoOfferTypes();
        void showLoadingUserInfoError();
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode);
        void loadUserInfo();
        void loadOffersType();
        void showOfferTypesInfo(String client, String date, String creditType);
        void openOfferTypeDetails(OfferType requestOfferType);
        void setFiltering(OffersFilterType requestType);
        OffersFilterType getFiltering();
    }
}
