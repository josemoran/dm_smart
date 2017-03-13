package com.navego360.credito.data.userinfo.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.navego360.credito.data.common.local.NavegoCreditDbHelper;
import com.navego360.credito.data.common.local.NavegoCreditPersistenceContract.UserInfoEntry;
import com.navego360.credito.data.userinfo.UserInfoDataSource;
import com.navego360.credito.models.credito.UserInfo;

public class UserInfoLocalDataSource implements UserInfoDataSource {

    private static UserInfoLocalDataSource INSTANCE;

    private NavegoCreditDbHelper mDbHelper;

    // Prevent direct instantiation.
    private UserInfoLocalDataSource(Context context) {
        mDbHelper = new NavegoCreditDbHelper(context);
    }

    public static UserInfoLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserInfoLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void saveDisbursementOption(UserInfo userInfo, String optionIndex) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserInfoEntry.COLUMN_NAME_DISBURSEMENT, optionIndex);

        String selection = UserInfoEntry.COLUMN_NAME_USER_INFO_ID + " LIKE ?";
        String[] selectionArgs = { userInfo.getId() };


        db.update(UserInfoEntry.USER_INFO_TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

    @Override
    public void getUserInfo(GetUserInfoCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                UserInfoEntry.COLUMN_NAME_USER_INFO_ID,
                UserInfoEntry.COLUMN_NAME_USER_NAME,
                UserInfoEntry.COLUMN_NAME_DOCUMENT,
                UserInfoEntry.COLUMN_NAME_EXPIRED_DATE,
                UserInfoEntry.COLUMN_NAME_USER_EMAIL,
                UserInfoEntry.COLUMN_NAME_LAST_AMOUNT,
                UserInfoEntry.COLUMN_NAME_MAX_OFFER,
                UserInfoEntry.COLUMN_NAME_REAL_AMOUNT,
                UserInfoEntry.COLUMN_NAME_BORROW_CAPACITY,
                UserInfoEntry.COLUMN_NAME_DISBURSEMENT,
                UserInfoEntry.COLUMN_NAME_APPROVED_DATE,
                UserInfoEntry.COLUMN_NAME_DISCOUNT,
                UserInfoEntry.COLUMN_NAME_CREDITED,
                UserInfoEntry.COLUMN_NAME_SERVICE_ID
        };

        Cursor c = db.query(UserInfoEntry.USER_INFO_TABLE_NAME, projection, null, null, null, null, null);

        UserInfo userInfo = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            userInfo = new UserInfo();
            userInfo.setId(c.getString(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_USER_INFO_ID)));
            userInfo.setUserName(c.getString(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_USER_NAME)));
            userInfo.setDocument(c.getString(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_DOCUMENT)));
            userInfo.setExpiredDate(c.getString(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_EXPIRED_DATE)));
            userInfo.setUserEmail(c.getString(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_USER_EMAIL)));
            userInfo.setLastAmount(c.getString(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_LAST_AMOUNT)));
            userInfo.setMaxOfferAmount(c.getString(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_MAX_OFFER)));
            userInfo.setRealAmount(c.getString(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_REAL_AMOUNT)));
            userInfo.setBorrowCapacity(c.getString(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_BORROW_CAPACITY)));
            userInfo.setDisbursement(c.getString(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_DISBURSEMENT)));
            userInfo.setApprovedDate(c.getString(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_APPROVED_DATE)));
            userInfo.setDiscount(c.getString(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_DISCOUNT)));
            userInfo.setCredited(c.getInt(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_CREDITED)) == 1);
            userInfo.setServiceId(c.getInt(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_SERVICE_ID)));
        }

        if (c != null) c.close();
        db.close();

        if (userInfo != null) callback.onUserInfoLoaded(userInfo);
        else callback.onDataNotAvailable();
    }

    @Override
    public void saveUserInfo(UserInfo userInfo) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UserInfoEntry.COLUMN_NAME_USER_INFO_ID, userInfo.getId());
        values.put(UserInfoEntry.COLUMN_NAME_USER_NAME, userInfo.getUserName());
        values.put(UserInfoEntry.COLUMN_NAME_DOCUMENT, userInfo.getDocument());
        values.put(UserInfoEntry.COLUMN_NAME_EXPIRED_DATE, userInfo.getExpiredDate());
        values.put(UserInfoEntry.COLUMN_NAME_USER_EMAIL, userInfo.getUserEmail());
        values.put(UserInfoEntry.COLUMN_NAME_LAST_AMOUNT, userInfo.getLastAmount());
        values.put(UserInfoEntry.COLUMN_NAME_MAX_OFFER, userInfo.getMaxOfferAmount());
        values.put(UserInfoEntry.COLUMN_NAME_REAL_AMOUNT, userInfo.getRealAmount());
        values.put(UserInfoEntry.COLUMN_NAME_BORROW_CAPACITY, userInfo.getBorrowCapacity());
        values.put(UserInfoEntry.COLUMN_NAME_DISBURSEMENT, userInfo.getDisbursement());
        values.put(UserInfoEntry.COLUMN_NAME_APPROVED_DATE, userInfo.getApprovedDate());
        values.put(UserInfoEntry.COLUMN_NAME_DISCOUNT, userInfo.getDiscount());
        values.put(UserInfoEntry.COLUMN_NAME_CREDITED, userInfo.isCredited());
        values.put(UserInfoEntry.COLUMN_NAME_SERVICE_ID, userInfo.getServiceId());

        db.insert(UserInfoEntry.USER_INFO_TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void deleteAllUserInfo() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(UserInfoEntry.USER_INFO_TABLE_NAME, null, null);
        db.close();
    }
}
