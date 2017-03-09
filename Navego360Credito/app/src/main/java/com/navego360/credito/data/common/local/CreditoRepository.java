package com.navego360.credito.data.common.local;


import android.content.Context;

import com.navego360.credito.data.offer.OffersDataSource;
import com.navego360.credito.data.offer.OffersRepository;
import com.navego360.credito.data.offer.local.OffersLocalDataSource;
import com.navego360.credito.data.offer.remote.OffersRemoteDataSource;
import com.navego360.credito.data.offertype.OfferTypesDataSource;
import com.navego360.credito.data.offertype.OfferTypesRepository;
import com.navego360.credito.data.offertype.local.OfferTypesLocalDataSource;
import com.navego360.credito.data.offertype.remote.OfferTypesRemoteDataSource;
import com.navego360.credito.data.userinfo.UserInfoDataSource;
import com.navego360.credito.data.userinfo.UserInfoRepository;
import com.navego360.credito.data.userinfo.local.UserInfoLocalDataSource;
import com.navego360.credito.data.userinfo.repository.UserInfoRemoteDataSource;

public class CreditoRepository {
    private static CreditoRepository INSTANCE = null;
    private static OfferTypesRepository offerTypesRepository;
    private static OffersRepository offersRepository;
    private static UserInfoRepository userInfoRepository;

    public CreditoRepository(Context context){
        OfferTypesDataSource remoteOfferTypes = OfferTypesRemoteDataSource.getInstance();
        OfferTypesDataSource localOfferTypes = OfferTypesLocalDataSource.getInstance(context);

        OfferTypesRepository repositoryOfferTypes = OfferTypesRepository.getInstance(remoteOfferTypes, localOfferTypes);

        OffersDataSource remoteOffer = OffersRemoteDataSource.getInstance();
        OffersDataSource localOffer = OffersLocalDataSource.getInstance(context);

        OffersRepository repositoryOffers = OffersRepository.getInstance(remoteOffer, localOffer);

        UserInfoDataSource remoteUserInfo = UserInfoRemoteDataSource.getInstance();
        UserInfoDataSource localUserInfo = UserInfoLocalDataSource.getInstance(context);

        UserInfoRepository repositoryUserInfo = UserInfoRepository.getInstance(remoteUserInfo, localUserInfo);

        offerTypesRepository = repositoryOfferTypes;
        offersRepository = repositoryOffers;
        userInfoRepository = repositoryUserInfo;
    }

    public static CreditoRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CreditoRepository(context);
        }
        return INSTANCE;
    }

    public OfferTypesRepository getOfferTypesRepository(){
        return offerTypesRepository;
    }

    public OffersRepository getOffersRepository(){
        return offersRepository;
    }

    public UserInfoRepository getUserInfoRepository() {
        return userInfoRepository;
    }
}
