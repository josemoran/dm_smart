package com.navego360.credito.utils;

import android.content.Context;

import com.navego360.credito.models.credito.Offer;
import com.navego360.credito.models.credito.UserInfo;
import com.navego360.credito.models.navego.CoordenadaGps;
import com.navego360.credito.variables.ComponentIds;

public class SaveNavegoUtils {

    // Oferta Detalle
    public static void saveOfferDetail(Context context, UserInfo userInfo, Offer offer){
        int serviceId = userInfo.getServiceId();
        String formDataId = offer.getId();
        String estadoCredito = offer.isCredited() ? "1" : "0";

        ContentClientUtils.updateOrInsert(context, String.valueOf(serviceId), formDataId,
                ComponentIds.OfferDetailData.OFERTA_DETALLE_ESTADO, estadoCredito);
        ContentClientUtils.updateFormData(context, formDataId);
    }

    // Oferta
    public static void saveOffer(Context context, UserInfo userInfo, Offer offer){
        String estadoCredito = offer.isCredited() ? "1" : "0";
        String serviceIdS = String.valueOf(userInfo.getServiceId());
        String formDataId = userInfo.getId();

        ContentClientUtils.updateOrInsert(context, serviceIdS, formDataId,
                ComponentIds.OfferData.OFERTA_CREDITO_MONTO, userInfo.getRealAmount());
        ContentClientUtils.updateOrInsert(context, serviceIdS, formDataId,
                ComponentIds.OfferData.OFERTA_FORMA_DESEMBOLSO, userInfo.getDisbursement());
        ContentClientUtils.updateOrInsert(context, serviceIdS, formDataId,
                ComponentIds.OfferData.OFERTA_CREDITO_ESTADO, estadoCredito);
        ContentClientUtils.updateOrInsert(context, serviceIdS, formDataId,
                ComponentIds.OfferData.OFERTA_CREDITO_PLAZO, offer.getCreditDate());
        ContentClientUtils.updateOrInsert(context, serviceIdS, formDataId,
                ComponentIds.OfferData.OFERTA_CREDITO_CUOTA, offer.getQuota());
        ContentClientUtils.updateOrInsert(context, serviceIdS, formDataId,
                ComponentIds.OfferData.OFERTA_CREDITO_TCEA, offer.getTcea());
        ContentClientUtils.updateOrInsert(context, serviceIdS, formDataId,
                ComponentIds.OfferData.OFERTA_ID_PRO_OFERTA_DETALLE, userInfo.getIdProOfferDetail());
        ContentClientUtils.updateOrInsert(context, serviceIdS, formDataId,
                ComponentIds.OfferData.OFERTA_FECHA_OTORGAMIENTO, userInfo.getGrantDate());
        ContentClientUtils.updateOrInsert(context, serviceIdS, formDataId,
                ComponentIds.OfferData.OFERTA_CODIGO_IMEI, userInfo.getImeiCode());
        ContentClientUtils.updateFormData(context, formDataId);
    }

    // Service
    public static void saveService(Context context, int serviceId){
        CoordenadaGps coordenadaGps = ContentClientUtils.getLastPosition(context);
        ContentClientUtils.updateService(context, String.valueOf(serviceId), coordenadaGps);
        ContentClientUtils.insertStatusService(context, String.valueOf(serviceId), coordenadaGps);
    }

}
