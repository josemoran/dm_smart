package com.navego360.credito.data.common.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NavegoCreditDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Credito.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_OFFER_TYPE =
            "CREATE TABLE " + NavegoCreditPersistenceContract.OfferTypeEntry.OFFER_TYPE_TABLE_NAME + " (" +
                    NavegoCreditPersistenceContract.OfferTypeEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    NavegoCreditPersistenceContract.OfferTypeEntry.COLUMN_NAME_OFFER_TYPE_ID + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferTypeEntry.COLUMN_NAME_CREDIT_TYPE + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferTypeEntry.COLUMN_NAME_AMOUNT + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferTypeEntry.COLUMN_NAME_QUOTA + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferTypeEntry.COLUMN_NAME_CREDIT_DATE + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferTypeEntry.COLUMN_NAME_TCEA + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferTypeEntry.COLUMN_NAME_RATE + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferTypeEntry.COLUMN_NAME_FLAT + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferTypeEntry.COLUMN_NAME_DISGRACE + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferTypeEntry.COLUMN_NAME_OFFER_TYPE + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferTypeEntry.COLUMN_NAME_BLOCKED + INTEGER_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferTypeEntry.COLUMN_NAME_CREDITED + INTEGER_TYPE +
                    " )";

    private static final String SQL_CREATE_OFFER =
            "CREATE TABLE " + NavegoCreditPersistenceContract.OfferEntry.OFFER_TABLE_NAME + " (" +
                    NavegoCreditPersistenceContract.OfferEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    NavegoCreditPersistenceContract.OfferEntry.COLUMN_NAME_OFFER_ID + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferEntry.COLUMN_NAME_QUOTA + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferEntry.COLUMN_NAME_CREDIT_DATE + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferEntry.COLUMN_NAME_TCEA + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.OfferEntry.COLUMN_NAME_OFFER_TYPE_ID + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_USER_INFO =
            "CREATE TABLE " + NavegoCreditPersistenceContract.UserInfoEntry.USER_INFO_TABLE_NAME + " (" +
                    NavegoCreditPersistenceContract.UserInfoEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    NavegoCreditPersistenceContract.UserInfoEntry.COLUMN_NAME_USER_INFO_ID + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.UserInfoEntry.COLUMN_NAME_USER_NAME + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.UserInfoEntry.COLUMN_NAME_DOCUMENT + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.UserInfoEntry.COLUMN_NAME_EXPIRED_DATE + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.UserInfoEntry.COLUMN_NAME_USER_EMAIL + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.UserInfoEntry.COLUMN_NAME_LAST_AMOUNT + TEXT_TYPE + COMMA_SEP +
                    NavegoCreditPersistenceContract.UserInfoEntry.COLUMN_NAME_BORROW_CAPACITY + TEXT_TYPE +
                    " )";

    public NavegoCreditDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_OFFER_TYPE);
        db.execSQL(SQL_CREATE_OFFER);
        db.execSQL(SQL_CREATE_USER_INFO);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
