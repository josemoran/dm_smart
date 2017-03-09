package com.navego360.credito.data.userinfo.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.navego360.credito.data.common.local.NavegoCreditDbHelper;
import com.navego360.credito.data.common.local.NavegoCreditPersistenceContract.UserInfoEntry;
import com.navego360.credito.data.userinfo.UserInfoDataSource;
import com.navego360.credito.models.UserInfo;

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
    public void getUserInfo(GetUserInfoCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                UserInfoEntry.COLUMN_NAME_USER_INFO_ID,
                UserInfoEntry.COLUMN_NAME_USER_NAME,
                UserInfoEntry.COLUMN_NAME_DOCUMENT,
                UserInfoEntry.COLUMN_NAME_EXPIRED_DATE,
                UserInfoEntry.COLUMN_NAME_USER_EMAIL,
                UserInfoEntry.COLUMN_NAME_LAST_AMOUNT,
                UserInfoEntry.COLUMN_NAME_BORROW_CAPACITY,
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
            userInfo.setBorrowCapacity(c.getString(c.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_BORROW_CAPACITY)));
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
        values.put(UserInfoEntry.COLUMN_NAME_BORROW_CAPACITY, userInfo.getBorrowCapacity());

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
