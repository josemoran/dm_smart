package com.navego360.credito.ui.offertypes;

import android.app.Activity;

import com.navego360.credito.data.common.local.CreditoRepository;
import com.navego360.credito.data.offertype.OfferTypesDataSource;
import com.navego360.credito.data.offertype.OfferTypesRepository;
import com.navego360.credito.data.userinfo.UserInfoDataSource;
import com.navego360.credito.data.userinfo.UserInfoRepository;
import com.navego360.credito.models.credito.OfferType;
import com.navego360.credito.models.credito.UserInfo;
import com.navego360.credito.variables.OffersFilterType;

import java.util.ArrayList;
import java.util.List;

import static com.navego360.credito.variables.RequestCode.OfferDetail.REQUEST_CREATE_CREDIT;

public class OfferTypesPresenter implements OfferTypesContract.Presenter {

    private final OfferTypesContract.View mOffersTypeView;
    private final OfferTypesRepository mOffersTypeRepository;
    private OffersFilterType mCurrentFiltering = OffersFilterType.ALL_OFFERS;

    private final CreditoRepository mCreditoRepository;

    private String mExpirationDate;
    private String mCreditType;

    public OfferTypesPresenter(CreditoRepository creditoRepository,
                               OfferTypesContract.View offerTypeView) {
        mCreditoRepository = creditoRepository;
        mOffersTypeRepository = creditoRepository.getOfferTypesRepository();
        mOffersTypeView = offerTypeView;

        mOffersTypeView.setPresenter(this);
    }

    @Override
    public void start() {
        loadUserInfo();
        loadOffersType();
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (REQUEST_CREATE_CREDIT == requestCode && Activity.RESULT_OK == resultCode) {
            mOffersTypeView.showSuccessfullySavedMessage();
        }
    }

    @Override
    public void loadUserInfo() {
        UserInfoRepository userInfoRepository = mCreditoRepository.getUserInfoRepository();
        userInfoRepository.getUserInfo(new UserInfoDataSource.GetUserInfoCallback() {
            @Override
            public void onUserInfoLoaded(UserInfo userInfo) {
                showOfferTypesInfo(userInfo.getUserName(), userInfo.getExpiredDate(),
                        userInfo.getCreditType());
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mOffersTypeView.isActive()) {
                    return;
                }
                mOffersTypeView.showLoadingUserInfoError();
            }
        });
    }

    @Override
    public void loadOffersType() {
        mOffersTypeRepository.getOfferTypes(new OfferTypesDataSource.LoadOfferTypesCallback() {
            @Override
            public void onOfferTypesLoaded(List<OfferType> offerTypes) {
                List<OfferType> offerTypesToShow = new ArrayList<>();

                // We filter the tasks based on the requestType
                for (OfferType offerType : offerTypes) {
                    offerTypesToShow.add(offerType);
                }

                // The view may not be able to handle UI updates anymore
                if (!mOffersTypeView.isActive()) {
                    return;
                }

                processOffersType(offerTypesToShow);
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mOffersTypeView.isActive()) {
                    return;
                }
                mOffersTypeView.showLoadingOffersError();
            }
        });
    }

    @Override
    public void showOfferTypesInfo(String client, String date, String creditType) {
        mCreditType = creditType;
        mExpirationDate = date;
        mOffersTypeView.showClientName(client);
        mOffersTypeView.showOfferDate(date);
    }

    @Override
    public void openOfferTypeDetails(OfferType requestOffer) {
        mOffersTypeView.showOfferTypeDetailsUi(requestOffer.getId());
    }

    @Override
    public void setFiltering(OffersFilterType requestType) {
        mCurrentFiltering = requestType;
    }

    @Override
    public OffersFilterType getFiltering() {
        return mCurrentFiltering;
    }

    private void processOffersType(List<OfferType> offerTypes) {
        if (offerTypes.isEmpty()) processEmptyOfferTypes();
        else mOffersTypeView.showOfferTypes(offerTypes, mExpirationDate, mCreditType);
    }

    private void processEmptyOfferTypes() {
        mOffersTypeView.showNoOfferTypes();
    }
}
