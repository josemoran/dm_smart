package com.navego360.credito.data.offertype;

import com.navego360.credito.models.credito.OfferType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OfferTypesRepository implements OfferTypesDataSource {

    private static OfferTypesRepository INSTANCE = null;
    private final OfferTypesDataSource mOfferTypesRemoteDataSource;
    private final OfferTypesDataSource mOfferTypesLocalDataSource;

    private Map<String, OfferType> mCachedOfferTypes;
    private boolean mCacheIsDirty = false;

    private OfferTypesRepository(OfferTypesDataSource offersTypesRemoteDataSource,
                                 OfferTypesDataSource offersTypesLocalDataSource) {
        mOfferTypesRemoteDataSource = offersTypesRemoteDataSource;
        mOfferTypesLocalDataSource = offersTypesLocalDataSource;
    }

    public static OfferTypesRepository getInstance(OfferTypesDataSource offersTypesRemoteDataSource,
                                                   OfferTypesDataSource offersTypesLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new OfferTypesRepository(offersTypesRemoteDataSource, offersTypesLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getOfferTypes(final LoadOfferTypesCallback callback) {

        // Respond immediately with cache if available and not dirty
//        if (mCachedOfferTypes != null && !mCacheIsDirty) {
//            callback.onOfferTypesLoaded(new ArrayList<>(mCachedOfferTypes.values()));
//            return;
//        }

        if (!mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
//            getOfferTypesFromRemoteDataSource(callback);
//        } else {
            // Query the local storage if available. If not, query the network.
            mOfferTypesLocalDataSource.getOfferTypes(new LoadOfferTypesCallback() {
                @Override
                public void onOfferTypesLoaded(List<OfferType> offers) {
                    refreshCache(offers);
                    callback.onOfferTypesLoaded(new ArrayList<>(mCachedOfferTypes.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
//                    getOfferTypesFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void saveOfferType(OfferType offer) {
//        mOfferTypesRemoteDataSource.saveOfferType(offer);
        mOfferTypesLocalDataSource.saveOfferType(offer);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedOfferTypes == null) {
            mCachedOfferTypes = new LinkedHashMap<>();
        }
        mCachedOfferTypes.put(offer.getId(), offer);
    }

    @Override
    public void blockNotCredited() {
        mOfferTypesLocalDataSource.blockNotCredited();
    }

    @Override
    public void blockAllExceptOfferType(String offerTypeId) {
        OfferType offerType = getOfferTypeWithId(offerTypeId);
        mOfferTypesLocalDataSource.blockAllExceptOfferType(offerTypeId);

        offerType.setBlocked(false);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedOfferTypes == null) {
            mCachedOfferTypes = new LinkedHashMap<>();
        }
        mCachedOfferTypes.put(offerType.getId(), offerType);
    }

    @Override
    public void creditedOfferType(OfferType offer) {
//        mOfferTypesRemoteDataSource.creditedOfferType(offer);
        mOfferTypesLocalDataSource.creditedOfferType(offer);

        offer.setCredited(true);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedOfferTypes == null) {
            mCachedOfferTypes = new LinkedHashMap<>();
        }
        mCachedOfferTypes.put(offer.getId(), offer);
    }

    @Override
    public void creditedOfferType(String offerTypeId) {
        creditedOfferType(getOfferTypeWithId(offerTypeId));
    }

    @Override
    public void getOfferType(final String offerTypeId, final GetOfferTypeCallback callback) {
        OfferType cachedOffer = getOfferTypeWithId(offerTypeId);

        // Respond immediately with cache if available
        if (cachedOffer != null) {
            callback.onOfferTypeLoaded(cachedOffer);
            return;
        }

        // Load from server/persisted if needed.

        // Is the offer in the local data source? If not, query the network.
        mOfferTypesLocalDataSource.getOfferType(offerTypeId, new GetOfferTypeCallback() {
            @Override
            public void onOfferTypeLoaded(OfferType offer) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedOfferTypes == null) {
                    mCachedOfferTypes = new LinkedHashMap<>();
                }
                mCachedOfferTypes.put(offer.getId(), offer);
                callback.onOfferTypeLoaded(offer);
            }

            @Override
            public void onDataNotAvailable() {
//                mOfferTypesRemoteDataSource.getOfferType(offerTypeId, new GetOfferTypeCallback() {
//                    @Override
//                    public void onOfferTypeLoaded(OfferType offer) {
//                        // Do in memory cache update to keep the app UI up to date
//                        if (mCachedOfferTypes == null) {
//                            mCachedOfferTypes = new LinkedHashMap<>();
//                        }
//                        mCachedOfferTypes.put(offer.getId(), offer);
//                        callback.onOfferTypeLoaded(offer);
//                    }
//
//                    @Override
//                    public void onDataNotAvailable() {
//                        callback.onDataNotAvailable();
//                    }
//                });
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteAllOfferTypes() {
//        mOfferTypesRemoteDataSource.deleteAllOfferTypes();
        mOfferTypesLocalDataSource.deleteAllOfferTypes();

        if (mCachedOfferTypes == null) {
            mCachedOfferTypes = new LinkedHashMap<>();
        }
        mCachedOfferTypes.clear();
    }

    @Override
    public void deleteOfferType(String offerId) {
//        mOfferTypesRemoteDataSource.deleteOfferType(offerId);
        mOfferTypesLocalDataSource.deleteOfferType(offerId);

        mCachedOfferTypes.remove(offerId);
    }

    @Override
    public int numOfferTypes() {
        return mOfferTypesLocalDataSource.numOfferTypes();
    }

//    private void getOfferTypesFromRemoteDataSource(final LoadOfferTypesCallback callback) {
//        mOfferTypesRemoteDataSource.getOfferTypes(new LoadOfferTypesCallback() {
//            @Override
//            public void onOfferTypesLoaded(List<OfferType> offers) {
//                refreshCache(offers);
//                refreshLocalDataSource(offers);
//                callback.onOfferTypesLoaded(new ArrayList<>(mCachedOfferTypes.values()));
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                callback.onDataNotAvailable();
//            }
//        });
//    }

    private void refreshCache(List<OfferType> offerTypes) {
        if (mCachedOfferTypes == null) {
            mCachedOfferTypes = new LinkedHashMap<>();
        }
        mCachedOfferTypes.clear();
        for (OfferType offerType : offerTypes) {
            mCachedOfferTypes.put(offerType.getId(), offerType);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<OfferType> offerTypes) {
        mOfferTypesLocalDataSource.deleteAllOfferTypes();
        for (OfferType offerType : offerTypes) {
            mOfferTypesLocalDataSource.saveOfferType(offerType);
        }
    }

    private OfferType getOfferTypeWithId(String id) {
        if (mCachedOfferTypes == null || mCachedOfferTypes.isEmpty()) return null;
        else return mCachedOfferTypes.get(id);
    }
}
