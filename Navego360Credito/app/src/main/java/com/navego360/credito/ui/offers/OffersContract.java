package com.navego360.credito.ui.offers;

import com.navego360.credito.models.credito.Offer;
import com.navego360.credito.ui.common.BasePresenter;
import com.navego360.credito.ui.common.BaseView;

import java.util.List;

public interface OffersContract {

    interface View extends BaseView<Presenter> {
        void showOffers(List<Offer> offers);
        void showLoadingOffersError();
        void showNoOffer();
        void showMissingOffer();
        void showLoadingUserInfoError();
        void showClientName(String clientName);
        void showAmount(String amount);
        void showCreditType(String creditType);
        void showApprovedDate(String approvedDate);
        void showTea(String tea);
        void showFlat(String flat);
        void showDisgrace(String disgrace);
        void showDiscount(String discount);
        void showBorrowCapacity(String borrowCapacity);
        void showLastAmount(String lastAmount);
        void showRealAmount(String realAmount);
        void setDisbursementOption(String option);
        void setDisbursementEnabled(String list);
        void showReadOnly();
        void blockOfferOptions(boolean blocked);
        void closePrintDialog();
        void showCompleteMailDialog();
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode);
        void loadUserInfo();
        void loadOffers(String offerTypeId);
        void generateCredit(String disbursementOption, Offer offer);
        void printCreditedOffer(String disbursementOptions, Offer offer);
        void sendEmailCredit(String disbursementOptions, Offer offer, String email);
    }
}
