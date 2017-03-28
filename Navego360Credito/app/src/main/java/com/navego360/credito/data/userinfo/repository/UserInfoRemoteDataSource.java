package com.navego360.credito.data.userinfo.repository;

import com.navego360.credito.data.userinfo.UserInfoDataSource;
import com.navego360.credito.models.credito.UserInfo;

public class UserInfoRemoteDataSource implements UserInfoDataSource {

    private static UserInfoRemoteDataSource INSTANCE;

    private static UserInfo USER_INFO_SERVICE_DATA = new UserInfo();

    // Prevent direct instantiation.
    private UserInfoRemoteDataSource() {}

    public static UserInfoRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserInfoRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void saveUserData(UserInfo userInfo, String optionIndex, String grantDate,
                             String idProOfferDetail, String imei) {

    }

    @Override
    public void getUserInfo(GetUserInfoCallback callback) {
        UserInfo userInfo = USER_INFO_SERVICE_DATA;
        callback.onUserInfoLoaded(userInfo);
    }

    @Override
    public void saveUserInfo(UserInfo userInfo) {
        USER_INFO_SERVICE_DATA = userInfo;
    }

    @Override
    public void deleteAllUserInfo() {
        USER_INFO_SERVICE_DATA = null;
    }
}
