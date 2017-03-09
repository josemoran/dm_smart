package com.navego360.credito.ui.offers;

import com.navego360.credito.models.Offer;
import com.navego360.credito.ui.common.BasePresenter;
import com.navego360.credito.ui.common.BaseView;

import java.util.List;

public interface OffersContract {

    interface View extends BaseView<Presenter> {
        void showOffers(List<Offer> offers);
        void showSuccessfullySavedMessage();
        void showLoadingOffersError();
        void showNoOffer();
        void showMissingOffer();
        void showLoadingUserInfoError();
        void showClientName(String clientName);
        void showAmount(String amount);
        void showCreditType(String creditType);
        void showBorrowCapacity(String borrowCapacity);
        void showLastAmount(String lastAmount);
        void showRealAmount(String realAmount);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void loadUserInfo();
        void loadOffers(String offerTypeId);
        void generateCredit();
        void printCreditedOffer(String disbursementOptions, Offer offer);
    }
}
