package com.navego360.credito.data.common.local;

import android.provider.BaseColumns;

public final class NavegoCreditPersistenceContract {

    private NavegoCreditPersistenceContract() {}

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
        public static final String COLUMN_NAME_OFFER_TYPE_ID = "offerTypeId";
    }

    public static abstract class UserInfoEntry implements BaseColumns {
        public static final String USER_INFO_TABLE_NAME = "userInfo";
        public static final String COLUMN_NAME_USER_INFO_ID = "userInfoId";
        public static final String COLUMN_NAME_USER_NAME = "userName";
        public static final String COLUMN_NAME_DOCUMENT = "document";
        public static final String COLUMN_NAME_EXPIRED_DATE = "expiredDate";
        public static final String COLUMN_NAME_USER_EMAIL = "userEmail";
        public static final String COLUMN_NAME_LAST_AMOUNT = "lastAmount";
        public static final String COLUMN_NAME_BORROW_CAPACITY = "borrowCapacity";
    }

}
