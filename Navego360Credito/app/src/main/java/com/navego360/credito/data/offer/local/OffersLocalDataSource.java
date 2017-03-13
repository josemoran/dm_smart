package com.navego360.credito.data.offer.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.navego360.credito.data.common.local.NavegoCreditDbHelper;
import com.navego360.credito.data.common.local.NavegoCreditPersistenceContract;
import com.navego360.credito.data.common.local.NavegoCreditPersistenceContract.OfferEntry;
import com.navego360.credito.data.offer.OffersDataSource;
import com.navego360.credito.models.credito.Offer;

import java.util.ArrayList;
import java.util.List;

public class OffersLocalDataSource implements OffersDataSource {

    private static OffersLocalDataSource INSTANCE;

    private NavegoCreditDbHelper mDbHelper;

    // Prevent direct instantiation.
    private OffersLocalDataSource(Context context) {
        mDbHelper = new NavegoCreditDbHelper(context);
    }

    public static OffersLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new OffersLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void getOffers(LoadOffersCallback callback) {
        List<Offer> offers = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                OfferEntry.COLUMN_NAME_OFFER_ID,
                OfferEntry.COLUMN_NAME_QUOTA,
                OfferEntry.COLUMN_NAME_CREDIT_DATE,
                OfferEntry.COLUMN_NAME_TCEA,
                OfferEntry.COLUMN_NAME_QUOTA_NOT_ADJUST,
                OfferEntry.COLUMN_NAME_RATE_GRACE,
                OfferEntry.COLUMN_NAME_RATE_PROCESS,
                OfferEntry.COLUMN_NAME_MONTH_GRACE,
                OfferEntry.COLUMN_NAME_DAYS_PROCESS,
                OfferEntry.COLUMN_NAME_CREDITED,
                OfferEntry.COLUMN_NAME_OFFER_TYPE_ID
        };

        Cursor c = db.query(OfferEntry.OFFER_TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Offer offer = new Offer();
                offer.setId(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_OFFER_ID)));
                offer.setQuota(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_QUOTA)));
                offer.setCreditDate(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_CREDIT_DATE)));
                offer.setTcea(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_TCEA)));
                offer.setQuotaNoAdjust(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_QUOTA_NOT_ADJUST)));
                offer.setRateGrace(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_RATE_GRACE)));
                offer.setRateProcess(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_RATE_PROCESS)));
                offer.setMonthGrace(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_MONTH_GRACE)));
                offer.setDaysProcess(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_DAYS_PROCESS)));
                offer.setCredited(c.getInt(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_CREDITED)) == 1);
                offer.setOfferTypeId(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_OFFER_TYPE_ID)));
                offers.add(offer);
            }
        }

        if (c != null) c.close();
        db.close();

        if (offers.isEmpty()) callback.onDataNotAvailable();
        else callback.onOffersLoaded(offers);
    }

    @Override
    public void getOffers(String offerTypeId, LoadOffersCallback callback) {
        List<Offer> offers = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                OfferEntry.COLUMN_NAME_OFFER_ID,
                OfferEntry.COLUMN_NAME_QUOTA,
                OfferEntry.COLUMN_NAME_CREDIT_DATE,
                OfferEntry.COLUMN_NAME_TCEA,
                OfferEntry.COLUMN_NAME_QUOTA_NOT_ADJUST,
                OfferEntry.COLUMN_NAME_RATE_GRACE,
                OfferEntry.COLUMN_NAME_RATE_PROCESS,
                OfferEntry.COLUMN_NAME_MONTH_GRACE,
                OfferEntry.COLUMN_NAME_DAYS_PROCESS,
                OfferEntry.COLUMN_NAME_CREDITED,
                OfferEntry.COLUMN_NAME_OFFER_TYPE_ID
        };

        String selection = OfferEntry.COLUMN_NAME_OFFER_TYPE_ID + " LIKE ? ";
        String[] selectionArgs = { offerTypeId };

        Cursor c = db.query(OfferEntry.OFFER_TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Offer offer = new Offer();
                offer.setId(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_OFFER_ID)));
                offer.setQuota(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_QUOTA)));
                offer.setCreditDate(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_CREDIT_DATE)));
                offer.setTcea(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_TCEA)));
                offer.setQuotaNoAdjust(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_QUOTA_NOT_ADJUST)));
                offer.setRateGrace(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_RATE_GRACE)));
                offer.setRateProcess(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_RATE_PROCESS)));
                offer.setMonthGrace(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_MONTH_GRACE)));
                offer.setDaysProcess(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_DAYS_PROCESS)));
                offer.setCredited(c.getInt(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_CREDITED)) == 1);
                offer.setOfferTypeId(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_OFFER_TYPE_ID)));
                offers.add(offer);
            }
        }

        if (c != null) c.close();
        db.close();

        if (offers.isEmpty()) callback.onDataNotAvailable();
        else callback.onOffersLoaded(offers);
    }

    @Override
    public void getOffer(String offerId, GetOfferCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                OfferEntry.COLUMN_NAME_OFFER_ID,
                OfferEntry.COLUMN_NAME_QUOTA,
                OfferEntry.COLUMN_NAME_CREDIT_DATE,
                OfferEntry.COLUMN_NAME_TCEA,
                OfferEntry.COLUMN_NAME_QUOTA_NOT_ADJUST,
                OfferEntry.COLUMN_NAME_RATE_GRACE,
                OfferEntry.COLUMN_NAME_RATE_PROCESS,
                OfferEntry.COLUMN_NAME_MONTH_GRACE,
                OfferEntry.COLUMN_NAME_DAYS_PROCESS,
                OfferEntry.COLUMN_NAME_CREDITED,
                OfferEntry.COLUMN_NAME_OFFER_TYPE_ID
        };

        String selection = OfferEntry.COLUMN_NAME_OFFER_ID + " LIKE ?";
        String[] selectionArgs = { offerId };

        Cursor c = db.query(OfferEntry.OFFER_TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Offer offer = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            offer = new Offer();
            offer.setId(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_OFFER_ID)));
            offer.setQuota(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_QUOTA)));
            offer.setCreditDate(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_CREDIT_DATE)));
            offer.setTcea(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_TCEA)));
            offer.setQuotaNoAdjust(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_QUOTA_NOT_ADJUST)));
            offer.setRateGrace(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_RATE_GRACE)));
            offer.setRateProcess(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_RATE_PROCESS)));
            offer.setMonthGrace(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_MONTH_GRACE)));
            offer.setDaysProcess(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_DAYS_PROCESS)));
            offer.setCredited(c.getInt(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_CREDITED)) == 1);
            offer.setOfferTypeId(c.getString(c.getColumnIndexOrThrow(OfferEntry.COLUMN_NAME_OFFER_TYPE_ID)));
        }

        if (c != null) c.close();
        db.close();

        if (offer != null) callback.onOfferLoaded(offer);
        else callback.onDataNotAvailable();
    }

    @Override
    public void saveOffer(Offer offer) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(OfferEntry.COLUMN_NAME_OFFER_ID, offer.getId());
        values.put(OfferEntry.COLUMN_NAME_QUOTA, offer.getQuota());
        values.put(OfferEntry.COLUMN_NAME_CREDIT_DATE, offer.getCreditDate());
        values.put(OfferEntry.COLUMN_NAME_TCEA, offer.getTcea());
        values.put(OfferEntry.COLUMN_NAME_QUOTA_NOT_ADJUST, offer.getQuotaNoAdjust());
        values.put(OfferEntry.COLUMN_NAME_RATE_GRACE, offer.getRateGrace());
        values.put(OfferEntry.COLUMN_NAME_RATE_PROCESS, offer.getRateProcess());
        values.put(OfferEntry.COLUMN_NAME_MONTH_GRACE, offer.getMonthGrace());
        values.put(OfferEntry.COLUMN_NAME_DAYS_PROCESS, offer.getDaysProcess());
        values.put(OfferEntry.COLUMN_NAME_CREDITED, offer.isCredited());
        values.put(OfferEntry.COLUMN_NAME_OFFER_TYPE_ID, offer.getOfferTypeId());

        db.insert(OfferEntry.OFFER_TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void creditedOffer(Offer offer) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NavegoCreditPersistenceContract.OfferEntry.COLUMN_NAME_CREDITED, true);

        String selection = OfferEntry.COLUMN_NAME_OFFER_ID + " LIKE ?";
        String[] selectionArgs = { offer.getId() };

        db.update(OfferEntry.OFFER_TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }

    @Override
    public void creditedOffer(String offerId) {

    }

    @Override
    public void refreshOffers() {

    }

    @Override
    public void deleteAllOffers() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(OfferEntry.OFFER_TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void deleteOffer(String offerId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selection = OfferEntry.COLUMN_NAME_OFFER_ID + " LIKE ?";
        String[] selectionArgs = { offerId };
        db.delete(OfferEntry.OFFER_TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    @Override
    public int numOffers() {
        int size = 0;

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.query(OfferEntry.OFFER_TABLE_NAME, null, null, null, null, null, null);

        if (c != null && c.getCount() > 0) while (c.moveToNext()) size++;
        if (c != null) c.close();
        db.close();

        return size;
    }
}
