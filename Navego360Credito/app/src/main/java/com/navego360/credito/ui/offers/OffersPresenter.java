package com.navego360.credito.ui.offers;

import android.content.Context;
import android.util.Log;

import com.navego360.credito.data.common.local.CreditoRepository;
import com.navego360.credito.data.offer.OffersDataSource;
import com.navego360.credito.data.offer.OffersRepository;
import com.navego360.credito.data.offertype.OfferTypesDataSource;
import com.navego360.credito.data.offertype.OfferTypesRepository;
import com.navego360.credito.data.userinfo.UserInfoDataSource;
import com.navego360.credito.data.userinfo.UserInfoRepository;
import com.navego360.credito.models.Offer;
import com.navego360.credito.models.OfferType;
import com.navego360.credito.models.UserInfo;
import com.navego360.credito.utils.DateUtils;
import com.navego360.credito.utils.DocumentUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OffersPresenter implements OffersContract.Presenter {

    private final OffersContract.View mOffersView;
    private final OffersRepository mOffersRepository;
    private final OfferTypesRepository mOfferTypesRepository;

    private final String mOfferTypeId;
    private String amount;
    private double realAmount;

    private UserInfo mUserInfo;
    private OfferType mOfferType;

    private final CreditoRepository mCreditoRepository;

    private Context mContext;

    public OffersPresenter(Context context, String offerTypeId, CreditoRepository creditoRepository,
                           OffersContract.View offerView) {
        mContext = context;
        mOfferTypeId = offerTypeId;
        mCreditoRepository = creditoRepository;
        mOffersRepository = creditoRepository.getOffersRepository();
        mOfferTypesRepository = creditoRepository.getOfferTypesRepository();
        mOffersView = offerView;

        mOffersView.setPresenter(this);
    }

    @Override
    public void start() {
        loadOffers(mOfferTypeId);
        loadUserInfo();
    }

    @Override
    public void loadUserInfo() {
        UserInfoRepository userInfoRepository = mCreditoRepository.getUserInfoRepository();
        userInfoRepository.getUserInfo(new UserInfoDataSource.GetUserInfoCallback() {
            @Override
            public void onUserInfoLoaded(UserInfo userInfo) {
                mUserInfo = userInfo;
                showOfferTypesInfo(userInfo);
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mOffersView.isActive()) {
                    return;
                }
                mOffersView.showLoadingUserInfoError();
            }
        });
    }

    @Override
    public void loadOffers(String offerTypeId) {

        mOfferTypesRepository.getOfferType(offerTypeId, new OfferTypesDataSource.GetOfferTypeCallback() {
            @Override
            public void onOfferTypeLoaded(OfferType offerType) {
                // The view may not be able to handle UI updates anymore
                if (!mOffersView.isActive()) {
                    return;
                }

                if (null == offerType) mOffersView.showMissingOffer();
                else {
                    mOfferType = offerType;
                    showOfferDetail(offerType);
                }
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mOffersView.isActive()) {
                    return;
                }
                mOffersView.showLoadingOffersError();
            }
        });
    }

    @Override
    public void generateCredit() {

    }

    @Override
    public void printCreditedOffer(String disbursementOptions, Offer offer) {
        try {
//            List<byte[]> data = DocumentUtils.secondDocument(false, "1000","72","15/10/15","8.46","1.85",
//                    mOfferType.getCreditType(),disbursementOptions, offer.getTcea(),
//                    mOfferType.getDisgrace(), mUserInfo.getUserName(),mUserInfo.getDocument());
            Date now = new Date();
            String nowFormat = DateUtils.convertDate(now, DateUtils.formatDate4);
            List<byte[]> data = DocumentUtils.secondDocument(false, String.valueOf(realAmount),
                    offer.getCreditDate(),nowFormat, mOfferType.getFlat(), "1.85",
                    mOfferType.getCreditType(),disbursementOptions, offer.getTcea(),
                    mOfferType.getDisgrace(), mUserInfo.getUserName(),mUserInfo.getDocument());
//            PrintUtils.printDocument(mContext, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showOfferDetail(OfferType offerType){
        amount = offerType.getAmount();
        String creditType = offerType.getCreditType();

        mOffersView.showAmount(amount);
        mOffersView.showCreditType(creditType);

        mOffersRepository.getOffers(mOfferTypeId, new OffersDataSource.LoadOffersCallback() {
            @Override
            public void onOffersLoaded(List<Offer> offers) {
                List<Offer> offersToShow = new ArrayList<>();

                // We filter the tasks based on the requestType
                for (Offer offer : offers) {
                    offersToShow.add(offer);
                }

                // The view may not be able to handle UI updates anymore
                if (!mOffersView.isActive()) {
                    return;
                }

                processOffers(offersToShow);
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mOffersView.isActive()) {
                    return;
                }
                mOffersView.showLoadingOffersError();
            }
        });
    }

    private void processOffers(List<Offer> offers){
        if (offers.isEmpty()) processEmptyOfferTs();
        else mOffersView.showOffers(offers);
    }

    private void processEmptyOfferTs() {
        mOffersView.showNoOffer();
    }

    private void showOfferTypesInfo(UserInfo userInfo){
        mOffersView.showClientName(userInfo.getUserName());
        mOffersView.showBorrowCapacity(userInfo.getBorrowCapacity());
        mOffersView.showLastAmount(userInfo.getLastAmount());
        if(amount != null) {
            realAmount = Double.parseDouble(amount);
            if(userInfo.getLastAmount() != null) {
                realAmount = Double.parseDouble(amount) - Double.parseDouble(userInfo.getLastAmount());
            }
            mOffersView.showRealAmount(String.valueOf(realAmount));
        }
    }
}
