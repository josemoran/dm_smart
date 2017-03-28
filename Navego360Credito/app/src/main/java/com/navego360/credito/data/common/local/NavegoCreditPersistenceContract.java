package com.navego360.credito.data.common.local;

import android.provider.BaseColumns;

public final class NavegoCreditPersistenceContract {

    private NavegoCreditPersistenceContract() {}

    // DM Smart

    public static abstract class OfferTypeEntry implements BaseColumns {
        public static final String OFFER_TYPE_TABLE_NAME = "offerType";
        public static final String COLUMN_NAME_OFFER_TYPE_ID = "offerTypeId";
        public static final String COLUMN_NAME_CREDIT_TYPE = "creditType";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_QUOTA = "quota";
        public static final String COLUMN_NAME_CREDIT_DATE = "creditDate";
        public static final String COLUMN_NAME_TCEA = "tcea";
        public static final String COLUMN_NAME_RATE = "rate";
        public static final String COLUMN_NAME_FLAT = "flat";
        public static final String COLUMN_NAME_DISGRACE = "disgrace";
        public static final String COLUMN_NAME_OFFER_TYPE = "offerType";
        public static final String COLUMN_NAME_BLOCKED = "blocked";
        public static final String COLUMN_NAME_CREDITED = "credited";
    }

    public static abstract class OfferEntry implements BaseColumns {
        public static final String OFFER_TABLE_NAME = "offer";
        public static final String COLUMN_NAME_OFFER_ID = "offerId";
        public static final String COLUMN_NAME_QUOTA = "quota";
        public static final String COLUMN_NAME_CREDIT_DATE = "creditDate";
        public static final String COLUMN_NAME_TCEA = "tcea";
        public static final String COLUMN_NAME_QUOTA_NOT_ADJUST = "quotaNoAdjust";
        public static final String COLUMN_NAME_RATE_GRACE = "rateGrace";
        public static final String COLUMN_NAME_RATE_PROCESS = "rateProcess";
        public static final String COLUMN_NAME_MONTH_GRACE = "monthGrace";
        public static final String COLUMN_NAME_DAYS_PROCESS = "daysProcess";
        public static final String COLUMN_NAME_CREDITED = "credited";
        public static final String COLUMN_NAME_OFFER_TYPE_ID = "offerTypeId";
        public static final String COLUMN_NAME_ID_DMM_CAMPANIA = "idDmmCampaign";
        public static final String COLUMN_NAME_ID_PRO_PROSPECTO = "idProProspect";
        public static final String COLUMN_NAME_ID_PRO_OFERTA = "idProOffer";
        public static final String COLUMN_NAME_ID_PRO_OFERTA_DETALLE = "idProOfferDetail";
    }

    public static abstract class UserInfoEntry implements BaseColumns {
        public static final String USER_INFO_TABLE_NAME = "userInfo";
        public static final String COLUMN_NAME_USER_INFO_ID = "userInfoId";
        public static final String COLUMN_NAME_USER_NAME = "userName";
        public static final String COLUMN_NAME_DOCUMENT = "document";
        public static final String COLUMN_NAME_EXPIRED_DATE = "expiredDate";
        public static final String COLUMN_NAME_USER_EMAIL = "userEmail";
        public static final String COLUMN_NAME_LAST_AMOUNT = "lastAmount";
        public static final String COLUMN_NAME_MAX_OFFER = "maxOfferAmount";
        public static final String COLUMN_NAME_REAL_AMOUNT = "realAmount";
        public static final String COLUMN_NAME_BORROW_CAPACITY = "borrowCapacity";
        public static final String COLUMN_NAME_DISBURSEMENT = "disbursement";
        public static final String COLUMN_NAME_APPROVED_DATE = "approvedDate";
        public static final String COLUMN_NAME_DISCOUNT = "discount";
        public static final String COLUMN_NAME_TCEA = "tcea";
        public static final String COLUMN_NAME_TEA = "tea";
        public static final String COLUMN_NAME_CREDIT_TYPE = "creditType";
        public static final String COLUMN_NAME_LIST_DIS = "listDis";
        public static final String COLUMN_NAME_ID_DMM_CAMPANIA = "idDmmCampaign";
        public static final String COLUMN_NAME_ID_PRO_PROSPECTO = "idProProspect";
        public static final String COLUMN_NAME_ID_PRO_OFERTA = "idProOffer";
        public static final String COLUMN_NAME_ID_PRO_OFERTA_DETALLE = "idProOfferDetail";
        public static final String COLUMN_NAME_FECHA_OTORGAMIENTO = "granteDate";
        public static final String COLUMN_NAME_CODIGO_IMEI = "imeiCode";
        public static final String COLUMN_NAME_CREDITED = "credited";
        public static final String COLUMN_NAME_SERVICE_ID = "serviceId";
    }

    // Navego

    public static abstract class ServiceEntry implements BaseColumns {
        public static final String COLUMN_NAME_SERVICE_ID = "IDSERVICIO";
        public static final String COLUMN_NAME_ID_CASO = "IDCASO";
        public static final String COLUMN_NAME_ID_ESTADO = "IDESTADO";
        public static final String COLUMN_NAME_CLIENTE_NOMBRE = "CLIENTE_NOMBRE";
        public static final String COLUMN_NAME_CLIENTE_APATERNO = "CLIENTE_APATERNO";
        public static final String COLUMN_NAME_CLIENTE_AMATERNO = "CLIENTE_AMATERNO";
        public static final String COLUMN_NAME_CLIENTE_TELEFONO = "CLIENTE_TELEFONO";
        public static final String COLUMN_NAME_ID_MOTORIZADO = "IDMOTORIZADO";
        public static final String COLUMN_NAME_ID_COMPANY = "IDCOMPANY";
        public static final String COLUMN_NAME_TIPO_SERVICIO_ID = "TIPO_SERVICIO_ID";
        public static final String COLUMN_NAME_FORM_TYPE = "FORMTYPE";
        public static final String COLUMN_NAME_CATEGORIA_ID = "CATEGORIA_ID";
        public static final String COLUMN_NAME_FLAG_SYNC_MOBILE = "FLAGSYNCMOBILE";
    }

    public static abstract class FormDataEntry implements BaseColumns {
        public static final String COLUMN_NAME_ID_FORM_DATA = "IDFORMDATA";
        public static final String COLUMN_NAME_ID_FORM = "IDFORM";
        public static final String COLUMN_NAME_SERVICE_ID = "SERVICEID";
        public static final String COLUMN_NAME_LABEL = "LABEL";
        public static final String COLUMN_NAME_EFS = "EFS";
        public static final String COLUMN_NAME_OPERATION = "OPERATION";
        public static final String COLUMN_NAME_SYNCWEB = "SYNCWEB";
    }

    public static abstract class FormAnswerEntry implements BaseColumns {
        public static final String COLUMN_NAME_ID_FORM_DATA = "IDFORMDATA";
        public static final String COLUMN_NAME_ID_COMPONENT = "IDCOMPONENT";
        public static final String COLUMN_NAME_SERVICE_ID = "SERVICEID";
        public static final String COLUMN_NAME_TYPE = "TYPE";
        public static final String COLUMN_NAME_ANSWER = "ANSWER";
        public static final String COLUMN_NAME_OPERATION = "OPERATION";
        public static final String COLUMN_NAME_SYNCWEB = "SYNCWEB";
    }

}
