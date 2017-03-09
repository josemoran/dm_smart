package com.navego360.credito.data.userinfo;

import com.navego360.credito.models.UserInfo;

public class UserInfoRepository implements UserInfoDataSource {
    private static UserInfoRepository INSTANCE = null;
    private final UserInfoDataSource mUserInfoRemoteDataSource;
    private final UserInfoDataSource mUserInfoLocalDataSource;

    private UserInfo mCachedUserInfo;

    private UserInfoRepository(UserInfoDataSource userInfoRemoteDataSource,
                               UserInfoDataSource userInfoLocalDataSource) {
        mUserInfoRemoteDataSource = userInfoRemoteDataSource;
        mUserInfoLocalDataSource = userInfoLocalDataSource;
    }

    public static UserInfoRepository getInstance(UserInfoDataSource userInfoRemoteDataSource,
                                                 UserInfoDataSource userInfoLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new UserInfoRepository(userInfoRemoteDataSource, userInfoLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getUserInfo(final GetUserInfoCallback callback) {
        UserInfo cachedUserInfo = getUserInfo();

        // Respond immediately with cache if available
        if (cachedUserInfo != null) {
            callback.onUserInfoLoaded(cachedUserInfo);
            return;
        }

        // Load from server/persisted if needed.

        // Is the offer in the local data source? If not, query the network.
        mUserInfoLocalDataSource.getUserInfo(new GetUserInfoCallback() {
            @Override
            public void onUserInfoLoaded(UserInfo userInfo) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedUserInfo == null) {
                    mCachedUserInfo = new UserInfo();
                }
                mCachedUserInfo = userInfo;
                callback.onUserInfoLoaded(userInfo);
            }

            @Override
            public void onDataNotAvailable() {
                mUserInfoRemoteDataSource.getUserInfo(new GetUserInfoCallback() {
                    @Override
                    public void onUserInfoLoaded(UserInfo userInfo) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedUserInfo == null) {
                            mCachedUserInfo = new UserInfo();
                        }
                        mCachedUserInfo = userInfo;
                        callback.onUserInfoLoaded(userInfo);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void saveUserInfo(UserInfo userInfo) {
        mUserInfoRemoteDataSource.saveUserInfo(userInfo);
        mUserInfoLocalDataSource.saveUserInfo(userInfo);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedUserInfo == null) {
            mCachedUserInfo = new UserInfo();
        }
        mCachedUserInfo = userInfo;
    }

    @Override
    public void deleteAllUserInfo() {
        mUserInfoRemoteDataSource.deleteAllUserInfo();
        mUserInfoLocalDataSource.deleteAllUserInfo();

        if (mCachedUserInfo == null) {
            mCachedUserInfo = new UserInfo();
        }
        mCachedUserInfo = null;
    }

    private UserInfo getUserInfo(){
        if (mCachedUserInfo == null) return null;
        else return mCachedUserInfo;
    }
}
