package com.navego360.credito.data.userinfo;

import com.navego360.credito.models.credito.UserInfo;

public interface UserInfoDataSource {

    interface GetUserInfoCallback {

        void onUserInfoLoaded(UserInfo userInfo);

        void onDataNotAvailable();
    }

    void saveDisbursementOption(UserInfo userInfo, String optionIndex);

    void getUserInfo(GetUserInfoCallback callback);

    void saveUserInfo(UserInfo userInfo);

    void deleteAllUserInfo();

}
