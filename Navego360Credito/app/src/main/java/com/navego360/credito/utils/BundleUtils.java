package com.navego360.credito.utils;

import android.os.Bundle;
import android.util.Log;

import com.navego360.credito.data.common.local.CreditoRepository;
import com.navego360.credito.data.offer.OffersRepository;
import com.navego360.credito.data.offertype.OfferTypesRepository;
import com.navego360.credito.data.userinfo.UserInfoRepository;
import com.navego360.credito.models.Offer;
import com.navego360.credito.models.OfferType;
import com.navego360.credito.models.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class BundleUtils {
    public static void saveBundle(Bundle bundle, CreditoRepository repository){
        OfferTypesRepository repository1 = repository.getOfferTypesRepository();
        OffersRepository repository2 = repository.getOffersRepository();
        UserInfoRepository repository3 = repository.getUserInfoRepository();

        int formTypeOffer = bundle.getInt("formTypeOffer");
        int formTypeOfferDetail = bundle.getInt("formTypeOfferDetail");
        int formTypeUserData = bundle.getInt("formUserDetail");
        int serviceId = bundle.getInt("serviceId");


        Log.e("BundleUtils","deleteAll");
        repository1.deleteAllOfferTypes();
        repository2.deleteAllOffers();
        repository3.deleteAllUserInfo();

        List<OfferType> offerTypes = generateOfferTypes(bundle);
        List<Offer> offers = generateOffers(bundle);
        UserInfo userInfo = generateCustomer(bundle);

        for (OfferType offerType : offerTypes){
            repository1.saveOfferType(offerType);
        }
        for (Offer offer : offers){
            repository2.saveOffer(offer);
        }
        repository3.saveUserInfo(userInfo);

    }

    private static UserInfo generateCustomer(Bundle bundle){
        UserInfo userInfo = new UserInfo();
        userInfo.setId("1");
        userInfo.setUserName("Juan Carlos Tejada");
        userInfo.setDocument("33445566");
        userInfo.setUserEmail("hark696@gmail.com");
        userInfo.setExpiredDate("15/04/2017");
        userInfo.setBorrowCapacity("235");
        userInfo.setLastAmount("3500");

        return userInfo;
    }

    private static List<OfferType> generateOfferTypes(Bundle bundle){
        List<OfferType> offers = new ArrayList<>();
        OfferType offer1 = new OfferType();
        offer1.setId("1");
        offer1.setCreditType("Extraordinario");
        offer1.setAmount("17000");
        offer1.setQuota("451.65");
        offer1.setCreditDate("72");
        offer1.setRate("1.85");
        offer1.setFlat("6.21");
        offer1.setDisgrace("0.028");
        offer1.setTcea("1.85");
        offer1.setOfferType("DM_CREDITOS");
        offer1.setBlocked(false);
        offer1.setCredited(false);
        offers.add(offer1);

        OfferType offer2 = new OfferType();
        offer2.setId("2");
        offer2.setCreditType("DM Plaza Activos");
        offer2.setAmount("3500");
        offer2.setQuota("180");
        offer2.setCreditDate("36");
        offer2.setRate("1.24");
        offer2.setFlat("6.21");
        offer2.setDisgrace("0.028");
        offer2.setTcea("1.85");
        offer2.setOfferType("DM_PLAZA");
        offer2.setBlocked(true);
        offer2.setCredited(false);
        offers.add(offer2);

        OfferType offer3 = new OfferType();
        offer3.setId("3");
        offer3.setCreditType("Hoteles - Cesantes");
        offer3.setAmount("5000");
        offer3.setQuota("350");
        offer3.setCreditDate("24");
        offer3.setRate("1.18");
        offer3.setFlat("6.21");
        offer3.setDisgrace("0.028");
        offer3.setTcea("1.85");
        offer3.setOfferType("DM_HOTELES");
        offer3.setBlocked(true);
        offer3.setCredited(false);
        offers.add(offer3);

        OfferType offer4 = new OfferType();
        offer4.setId("4");
        offer4.setCreditType("Vivienda Do");
        offer4.setAmount("17000");
        offer4.setQuota("350");
        offer4.setCreditDate("24");
        offer4.setRate("1.18");
        offer4.setFlat("6.21");
        offer4.setDisgrace("0.028");
        offer4.setTcea("1.85");
        offer4.setOfferType("DM_VIVIENDA");
        offer4.setBlocked(true);
        offer4.setCredited(false);
        offers.add(offer4);
        return offers;
    }

    private static List<Offer> generateOffers(Bundle bundle){
        List<Offer> offers = new ArrayList<>();
        Offer offer1 = new Offer();
        offer1.setId("1");
        offer1.setQuota("258.79");
        offer1.setCreditDate("24");
        offer1.setTcea("26.6");
        offer1.setOfferTypeId("1");
        offers.add(offer1);

        Offer offer2 = new Offer();
        offer2.setId("2");
        offer2.setQuota("216.12");
        offer2.setCreditDate("36");
        offer2.setTcea("26.6");
        offer2.setOfferTypeId("1");
        offers.add(offer2);

        Offer offer3 = new Offer();
        offer3.setId("3");
        offer3.setQuota("205.65");
        offer3.setCreditDate("72");
        offer3.setTcea("26.6");
        offer3.setOfferTypeId("1");
        offers.add(offer3);

        Offer offer4 = new Offer();
        offer4.setId("4");
        offer4.setQuota("218.79");
        offer4.setCreditDate("20");
        offer4.setTcea("26.6");
        offer4.setOfferTypeId("1");
        offers.add(offer4);

        Offer offer5 = new Offer();
        offer5.setId("6");
        offer5.setQuota("218.12");
        offer5.setCreditDate("64");
        offer5.setTcea("26.6");
        offer5.setOfferTypeId("1");
        offers.add(offer5);

        return offers;

    }
}
