package com.navego360.credito.utils;

import android.text.TextUtils;
import android.util.Log;

import com.navego360.credito.models.credito.Offer;
import com.navego360.credito.models.credito.OfferType;
import com.navego360.credito.models.credito.UserInfo;
import com.navego360.credito.models.navego.FormAnswer;
import com.navego360.credito.variables.ComponentIds;

import java.util.List;

public class GenerateUtils {

    // Oferta
    public static Offer generateOffer(int id, List<FormAnswer> formAnswerList){
        Offer offer = new Offer();
        Log.e("Generate","OFERTA");
        offer.setId(String.valueOf(id));
        for(FormAnswer formAnswer : formAnswerList) {
            Log.e("FormAnswer","Data: "  + formAnswer.toString());
            if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_PLAZO)) {
                offer.setCreditDate(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_CUOTA)) {
                offer.setQuota(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_TCEA)) {
                offer.setTcea(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_CUOTA_SINAJUSTE)) {
                offer.setQuotaNoAdjust(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_INTERES_GRACIA)) {
                offer.setRateGrace(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_INTERES_PROCESO)) {
                offer.setRateProcess(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_MESES_GRACIA)) {
                offer.setMonthGrace(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_DIAS_PROCESO)) {
                offer.setDaysProcess(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_TIPO)) {
                offer.setOfferTypeId(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_ESTADO)) {
                offer.setCredited(formAnswer.getAnswer().equals("1"));
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_ID_DMM_CAMPANIA)) {
                offer.setIdDmmCampaign(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_ID_PRO_PROSPECTO)) {
                offer.setIdProProspect(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_ID_PRO_OFERTA)) {
                offer.setIdProOffer(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_ID_PRO_OFERTA_DETALLE)) {
                offer.setIdProOfferDetail(formAnswer.getAnswer());
            }
        }
        return offer;
    }

    // Tipo de Oferta
    public static OfferType generateOfferType(List<FormAnswer> formAnswerList){
        OfferType offerType = new OfferType();
        Log.e("Generate","TIPO DE OFERTA");
        for(FormAnswer formAnswer : formAnswerList) {
            Log.e("FormAnswer","Data: "  + formAnswer.toString());
            if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_TIPO)) {
                offerType.setId(formAnswer.getAnswer());
                offerType.setOfferType(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_MONTO)) {
                offerType.setAmount(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_CUOTA)) {
                offerType.setQuota(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_PLAZO)) {
                offerType.setCreditDate(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_INTERES)) {
                offerType.setRate(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_FLAT)) {
                offerType.setFlat(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_DESGRAVAMEN)) {
                offerType.setDisgrace(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_TCEA)) {
                offerType.setTcea(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferDetailData.OFERTA_DETALLE_ESTADO)) {
                offerType.setCredited(formAnswer.getAnswer().equals("1"));
            }
        }
        offerType.setCreditType("");
        offerType.setBlocked(false);
        return offerType;
    }

    // Informacion de usuario
    public static UserInfo generateUserInfo(List<FormAnswer> formAnswerInfo,
                                            List<FormAnswer> formAnswerOffer,
                                            int formDataId, int serviceId){
        UserInfo userInfo = new UserInfo();
        Log.e("Generate","INFO DE USUARIO");

        userInfo.setId(String.valueOf(formDataId));
        userInfo.setServiceId(serviceId);

        String[] names = new String[3];
        for(FormAnswer formAnswer : formAnswerInfo) {
            Log.e("FormAnswer","Data: "  + formAnswer.toString());
            if (formAnswer.getComponentId().equals(ComponentIds.UserInfoData.INFO_DNI)) {
                userInfo.setDocument(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.UserInfoData.INFO_NOMBRES)) {
                names[0] = formAnswer.getAnswer();
            } else if (formAnswer.getComponentId().equals(ComponentIds.UserInfoData.INFO_APELLIDO_PATERNO)) {
                names[1] = formAnswer.getAnswer();
            } else if (formAnswer.getComponentId().equals(ComponentIds.UserInfoData.INFO_APELLIDO_MATERNO)) {
                names[2] = formAnswer.getAnswer();
            }
        }

        String fullName = TextUtils.join(" ", names);
        userInfo.setUserName(fullName);

        for(FormAnswer formAnswer : formAnswerOffer) {
            Log.e("FormAnswer","Data: "  + formAnswer.toString());
            if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_FECHA_VENCE)) {
                userInfo.setExpiredDate(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_SALDO_ANTERIOR)) {
                userInfo.setLastAmount(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_CAP_ENDEUDAMIENTO)) {
                userInfo.setBorrowCapacity(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_FORMA_DESEMBOLSO)) {
                userInfo.setDisbursement(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_OFERTA_MAXIMA)) {
                userInfo.setMaxOfferAmount(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_MONTO_DESEMBOLSO)) {
                userInfo.setRealAmount(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_FORMA_DESCUENTO)) {
                userInfo.setDiscount(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_FECHA_APROBACION)) {
                userInfo.setApprovedDate(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_TIPO_CREDITO)) {
                userInfo.setCreditType(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_TCEA)) {
                userInfo.setTcea(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_TEA)) {
                userInfo.setTea(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_FORMA_DESEMBOLSO_LISTA)) {
                userInfo.setListDis(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_ID_DMM_CAMPANIA)) {
                userInfo.setIdDmmCampaign(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_ID_PRO_PROSPECTO)) {
                userInfo.setIdProProspect(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_ID_PRO_OFERTA)) {
                userInfo.setIdProOffer(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_ID_PRO_OFERTA_DETALLE)) {
                userInfo.setIdProOfferDetail(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_FECHA_OTORGAMIENTO)) {
                userInfo.setGrantDate(formAnswer.getAnswer());
            } else if (formAnswer.getComponentId().equals(ComponentIds.OfferData.OFERTA_CODIGO_IMEI)) {
                userInfo.setImeiCode(formAnswer.getAnswer());
            }
        }

        return userInfo;
    }
}
