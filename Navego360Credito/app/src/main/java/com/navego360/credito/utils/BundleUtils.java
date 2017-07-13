package com.navego360.credito.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.navego360.credito.data.common.local.CreditoRepository;
import com.navego360.credito.data.offer.OffersRepository;
import com.navego360.credito.data.offertype.OfferTypesRepository;
import com.navego360.credito.data.userinfo.UserInfoRepository;
import com.navego360.credito.models.credito.Offer;
import com.navego360.credito.models.credito.OfferType;
import com.navego360.credito.models.credito.UserInfo;
import com.navego360.credito.models.navego.FormAnswer;
import com.navego360.credito.models.navego.FormData;

import java.util.ArrayList;
import java.util.List;

import static com.navego360.credito.utils.ContentClientUtils.getFormAnswer;
import static com.navego360.credito.utils.ContentClientUtils.getFormData;
import static com.navego360.credito.utils.GenerateUtils.generateOffer;
import static com.navego360.credito.utils.GenerateUtils.generateOfferType;
import static com.navego360.credito.utils.GenerateUtils.generateUserInfo;

public class BundleUtils {
    public static void saveBundle(Context context, Bundle bundle, CreditoRepository repository){
        OfferTypesRepository repository1 = repository.getOfferTypesRepository();
        OffersRepository repository2 = repository.getOffersRepository();
        UserInfoRepository repository3 = repository.getUserInfoRepository();

        int formTypeOffer = bundle.getInt("formTypeOffer");//formDataId
        int formTypeOfferDetail = bundle.getInt("formTypeOfferDetail");//formDataId
        int formTypeUserData = bundle.getInt("formUserDetail");//formDataId
        int serviceId = bundle.getInt("serviceId");//serviceId

        Log.e("BundleUtils","deleteAll");
        repository1.deleteAllOfferTypes();
        repository2.deleteAllOffers();
        repository3.deleteAllUserInfo();

        List<OfferType> offerTypes = generateOfferTypes(context, formTypeOfferDetail, serviceId);
        List<Offer> offers = generateOffers(context, formTypeOfferDetail, serviceId);
        UserInfo userInfo = generatedUserInfo(context, formTypeUserData, formTypeOffer, serviceId);

        for (OfferType offerType : offerTypes){
            repository1.saveOfferType(offerType);
        }
        repository1.blockNotCredited();

        for (Offer offer : offers){
            repository2.saveOffer(offer);
        }

        repository3.saveUserInfo(userInfo);

    }

    private static List<Offer> generateOffers(Context context, int formTypeOfferDetail, int serviceId){
        List<Offer> offers = new ArrayList<>();
        List<FormData> formDataList = getFormData(context, formTypeOfferDetail, serviceId);
        for (FormData formData : formDataList){
            int id = formData.getFormDataId();
            List<FormAnswer> formAnswerList = getFormAnswer(context, formData.getFormDataId(), serviceId);
            Offer offer = generateOffer(id, formAnswerList);
            offers.add(offer);
        }
        return offers;
    }

    private static List<OfferType> generateOfferTypes(Context context, int formTypeOfferDetail, int serviceId){
        List<OfferType> offerTypes = new ArrayList<>();
        List<FormData> formDataList = getFormData(context, formTypeOfferDetail, serviceId);
        for (FormData formData : formDataList) {
            boolean insertNew = true;
            List<FormAnswer> formAnswerList = getFormAnswer(context, formData.getFormDataId(), serviceId);
            OfferType offerTypeTmp = generateOfferType(formAnswerList);
            if (offerTypes.size() == 0) {
                Log.e("generateOfferTypes","No habia tipos de ofertas");
                offerTypes.add(offerTypeTmp);
            } else {
                Log.e("generateOfferTypes","Existe tipos de ofertas : " + offerTypes.size());
                for (OfferType offerType : offerTypes) {
                    if (offerType.getId().equals(offerTypeTmp.getId())) {
                        if(offerType.isCredited()){
                            offerTypeTmp.setCredited(offerType.isCredited());
                        }
                        if (Double.parseDouble(offerType.getAmount()) <
                                Double.parseDouble(offerTypeTmp.getAmount())) {
                            int position = offerTypes.indexOf(offerType);
                            offerTypes.set(position, offerTypeTmp);
                        } else if(Double.parseDouble(offerType.getAmount()) ==
                                Double.parseDouble(offerTypeTmp.getAmount())){
                            if(Double.parseDouble(offerType.getCreditDate()) <=
                                    Double.parseDouble(offerTypeTmp.getCreditDate())){
                                int position = offerTypes.indexOf(offerType);
                                offerTypes.set(position, offerTypeTmp);
                            } else {
                                if (offerTypeTmp.isCredited()) {
                                    int position = offerTypes.indexOf(offerType);
                                    offerType.setCredited(offerTypeTmp.isCredited());
                                    offerTypes.set(position, offerType);
                                }
                            }
                        } else {
                            if(offerTypeTmp.isCredited()) {
                                int position = offerTypes.indexOf(offerType);
                                offerType.setCredited(offerTypeTmp.isCredited());
                                offerTypes.set(position, offerType);
                            }
                        }
                        insertNew = false;
                    }
                }
                if (insertNew) offerTypes.add(offerTypeTmp);
            }
        }

        return offerTypes;
    }

    private static UserInfo generatedUserInfo(Context context, int formTypeUserData,
                                              int formTypeOffer, int serviceId){
        List<FormData> formDataListInfo = getFormData(context, formTypeUserData, serviceId);
        List<FormData> formDataListOffer = getFormData(context, formTypeOffer, serviceId);
        FormData formDataInfo = formDataListInfo.get(0);
        FormData formDataOffer = formDataListOffer.get(0);
        Log.e("USER INFO","form data datos " + formDataInfo.toString());
        List<FormAnswer> formAnswerList1 = getFormAnswer(context, formDataInfo.getFormDataId(), serviceId);
        List<FormAnswer> formAnswerList2 = getFormAnswer(context, formDataOffer.getFormDataId(), serviceId);
        return generateUserInfo(formAnswerList1, formAnswerList2, formDataOffer.getFormDataId(), serviceId);
    }

    public static boolean isUpdatedFormData(Context context, Bundle bundle){
        int formTypeUserData = bundle.getInt("formUserDetail");//formDataId
        int serviceId = bundle.getInt("serviceId");//serviceId
        List<FormData> formDataListInfo = getFormData(context, formTypeUserData, serviceId);
        FormData formDataInfo = formDataListInfo.get(0);
        // return formDataInfo.getOperation() == 2;
        return formDataInfo.getUpdateForm() == 1;
        // Usar campo que indique datos actualizados
    }

}
