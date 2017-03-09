package com.navego360.credito.data.userinfo;

import com.navego360.credito.models.UserInfo;

public interface UserInfoDataSource {

    interface GetUserInfoCallback {

        void onUserInfoLoaded(UserInfo userInfo);

        void onDataNotAvailable();
    }

    void getUserInfo(GetUserInfoCallback callback);

    void saveUserInfo(UserInfo userInfo);

    void deleteAllUserInfo();

}
