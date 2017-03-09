package com.navego360.credito.data.offertype.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.navego360.credito.data.common.local.NavegoCreditDbHelper;
import com.navego360.credito.data.offertype.OfferTypesDataSource;
import com.navego360.credito.data.common.local.NavegoCreditPersistenceContract.OfferTypeEntry;
import com.navego360.credito.models.OfferType;

import java.util.ArrayList;
import java.util.List;

public class OfferTypesLocalDataSource implements OfferTypesDataSource {

    private static OfferTypesLocalDataSource INSTANCE;

    private NavegoCreditDbHelper mDbHelper;

    // Prevent direct instantiation.
    private OfferTypesLocalDataSource(Context context) {
        mDbHelper = new NavegoCreditDbHelper(context);
    }

    public static OfferTypesLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new OfferTypesLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getOfferTypes(LoadOfferTypesCallback callback) {
        List<OfferType> offerTypes = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                OfferTypeEntry.COLUMN_NAME_OFFER_TYPE_ID,
                OfferTypeEntry.COLUMN_NAME_CREDIT_TYPE,
                OfferTypeEntry.COLUMN_NAME_AMOUNT,
                OfferTypeEntry.COLUMN_NAME_QUOTA,
                OfferTypeEntry.COLUMN_NAME_CREDIT_DATE,
                OfferTypeEntry.COLUMN_NAME_TCEA,
                OfferTypeEntry.COLUMN_NAME_RATE,
                OfferTypeEntry.COLUMN_NAME_FLAT,
                OfferTypeEntry.COLUMN_NAME_DISGRACE,
                OfferTypeEntry.COLUMN_NAME_OFFER_TYPE,
                OfferTypeEntry.COLUMN_NAME_BLOCKED,
                OfferTypeEntry.COLUMN_NAME_CREDITED
        };

        Cursor c = db.query(OfferTypeEntry.OFFER_TYPE_TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                OfferType offerType = new OfferType();
                offerType.setId(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_OFFER_TYPE_ID)));
                offerType.setCreditType(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_CREDIT_TYPE)));
                offerType.setAmount(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_AMOUNT)));
                offerType.setQuota(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_QUOTA)));
                offerType.setCreditDate(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_CREDIT_DATE)));
                offerType.setTcea(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_TCEA)));
                offerType.setRate(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_RATE)));
                offerType.setFlat(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_FLAT)));
                offerType.setDisgrace(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_DISGRACE)));
                offerType.setOfferType(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_OFFER_TYPE)));
                offerType.setBlocked(c.getInt(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_BLOCKED)) == 1);
                offerType.setCredited(c.getInt(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_CREDITED)) == 1);
                offerTypes.add(offerType);
            }
        }

        if (c != null) c.close();
        db.close();

        if (offerTypes.isEmpty()) callback.onDataNotAvailable();
        else callback.onOfferTypesLoaded(offerTypes);
    }

    @Override
    public void getOfferType(String offerTypeId, GetOfferTypeCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                OfferTypeEntry.COLUMN_NAME_OFFER_TYPE_ID,
                OfferTypeEntry.COLUMN_NAME_CREDIT_TYPE,
                OfferTypeEntry.COLUMN_NAME_AMOUNT,
                OfferTypeEntry.COLUMN_NAME_QUOTA,
                OfferTypeEntry.COLUMN_NAME_CREDIT_DATE,
                OfferTypeEntry.COLUMN_NAME_TCEA,
                OfferTypeEntry.COLUMN_NAME_RATE,
                OfferTypeEntry.COLUMN_NAME_FLAT,
                OfferTypeEntry.COLUMN_NAME_DISGRACE,
                OfferTypeEntry.COLUMN_NAME_OFFER_TYPE,
                OfferTypeEntry.COLUMN_NAME_BLOCKED,
                OfferTypeEntry.COLUMN_NAME_CREDITED
        };

        String selection = OfferTypeEntry.COLUMN_NAME_OFFER_TYPE_ID + " LIKE ?";
        String[] selectionArgs = { offerTypeId };

        Cursor c = db.query(OfferTypeEntry.OFFER_TYPE_TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        OfferType offerType = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            offerType = new OfferType();
            offerType.setId(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_OFFER_TYPE_ID)));
            offerType.setCreditType(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_CREDIT_TYPE)));
            offerType.setAmount(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_AMOUNT)));
            offerType.setQuota(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_QUOTA)));
            offerType.setCreditDate(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_CREDIT_DATE)));
            offerType.setTcea(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_TCEA)));
            offerType.setRate(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_RATE)));
            offerType.setFlat(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_FLAT)));
            offerType.setDisgrace(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_DISGRACE)));
            offerType.setOfferType(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_OFFER_TYPE)));
            offerType.setBlocked(c.getInt(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_BLOCKED)) == 1);
            offerType.setCredited(c.getInt(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_CREDITED)) == 1);
        }

        if (c != null) c.close();
        db.close();

        if (offerType != null) callback.onOfferTypeLoaded(offerType);
        else callback.onDataNotAvailable();
    }

    @Override
    public void saveOfferType(OfferType offerType) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(OfferTypeEntry.COLUMN_NAME_OFFER_TYPE_ID, offerType.getId());
        values.put(OfferTypeEntry.COLUMN_NAME_CREDIT_TYPE, offerType.getCreditType());
        values.put(OfferTypeEntry.COLUMN_NAME_AMOUNT, offerType.getAmount());
        values.put(OfferTypeEntry.COLUMN_NAME_QUOTA, offerType.getQuota());
        values.put(OfferTypeEntry.COLUMN_NAME_CREDIT_DATE, offerType.getCreditDate());
        values.put(OfferTypeEntry.COLUMN_NAME_TCEA, offerType.getTcea());
        values.put(OfferTypeEntry.COLUMN_NAME_RATE, offerType.getRate());
        values.put(OfferTypeEntry.COLUMN_NAME_FLAT, offerType.getFlat());
        values.put(OfferTypeEntry.COLUMN_NAME_DISGRACE, offerType.getDisgrace());
        values.put(OfferTypeEntry.COLUMN_NAME_OFFER_TYPE, offerType.getOfferType());
        values.put(OfferTypeEntry.COLUMN_NAME_BLOCKED, offerType.isBlocked());
        values.put(OfferTypeEntry.COLUMN_NAME_CREDITED, offerType.isCredited());

        db.insert(OfferTypeEntry.OFFER_TYPE_TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void creditedOfferType(OfferType offerType) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OfferTypeEntry.COLUMN_NAME_CREDITED, true);

        String selection = OfferTypeEntry.COLUMN_NAME_OFFER_TYPE_ID + " LIKE ?";
        String[] selectionArgs = { offerType.getId() };

        db.update(OfferTypeEntry.OFFER_TYPE_TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }

    @Override
    public void creditedOfferType(String offerId) {

    }

    @Override
    public void refreshOfferTypes() {

    }

    @Override
    public void deleteAllOfferTypes() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(OfferTypeEntry.OFFER_TYPE_TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void deleteOfferType(String offerTypeId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selection = OfferTypeEntry.COLUMN_NAME_OFFER_TYPE_ID + " LIKE ?";
        String[] selectionArgs = { offerTypeId };
        db.delete(OfferTypeEntry.OFFER_TYPE_TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    @Override
    public int numOfferTypes() {
        List<OfferType> offerTypes = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                OfferTypeEntry.COLUMN_NAME_OFFER_TYPE_ID,
                OfferTypeEntry.COLUMN_NAME_CREDIT_TYPE,
                OfferTypeEntry.COLUMN_NAME_AMOUNT,
                OfferTypeEntry.COLUMN_NAME_QUOTA,
                OfferTypeEntry.COLUMN_NAME_CREDIT_DATE,
                OfferTypeEntry.COLUMN_NAME_TCEA,
                OfferTypeEntry.COLUMN_NAME_RATE,
                OfferTypeEntry.COLUMN_NAME_FLAT,
                OfferTypeEntry.COLUMN_NAME_DISGRACE,
                OfferTypeEntry.COLUMN_NAME_OFFER_TYPE,
                OfferTypeEntry.COLUMN_NAME_BLOCKED,
                OfferTypeEntry.COLUMN_NAME_CREDITED
        };

        Cursor c = db.query(OfferTypeEntry.OFFER_TYPE_TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                OfferType offerType = new OfferType();
                offerType.setId(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_OFFER_TYPE_ID)));
                offerType.setCreditType(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_CREDIT_TYPE)));
                offerType.setAmount(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_AMOUNT)));
                offerType.setQuota(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_QUOTA)));
                offerType.setCreditDate(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_CREDIT_DATE)));
                offerType.setTcea(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_TCEA)));
                offerType.setRate(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_RATE)));
                offerType.setFlat(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_FLAT)));
                offerType.setDisgrace(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_DISGRACE)));
                offerType.setOfferType(c.getString(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_OFFER_TYPE)));
                offerType.setBlocked(c.getInt(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_BLOCKED)) == 1);
                offerType.setCredited(c.getInt(c.getColumnIndexOrThrow(OfferTypeEntry.COLUMN_NAME_CREDITED)) == 1);
                offerTypes.add(offerType);
            }
        }

        if (c != null) c.close();
        db.close();

        return offerTypes.size();
    }

}
