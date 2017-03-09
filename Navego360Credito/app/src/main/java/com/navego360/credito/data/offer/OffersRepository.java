package com.navego360.credito.data.offer;

import com.navego360.credito.models.Offer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OffersRepository implements OffersDataSource {
    private static OffersRepository INSTANCE = null;
    private final OffersDataSource mOffersRemoteDataSource;
    private final OffersDataSource mOffersLocalDataSource;

    private Map<String, Offer> mCachedOffers;
    private boolean mCacheIsDirty = false;

    private OffersRepository(OffersDataSource offersRemoteDataSource,
                             OffersDataSource offersLocalDataSource) {
        mOffersRemoteDataSource = offersRemoteDataSource;
        mOffersLocalDataSource = offersLocalDataSource;
    }

    public static OffersRepository getInstance(OffersDataSource offersRemoteDataSource,
                                                   OffersDataSource offersLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new OffersRepository(offersRemoteDataSource, offersLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getOffers(final LoadOffersCallback callback) {
        // Respond immediately with cache if available and not dirty
        if (mCachedOffers != null && !mCacheIsDirty) {
            callback.onOffersLoaded(new ArrayList<>(mCachedOffers.values()));
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getOffersFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mOffersLocalDataSource.getOffers(new LoadOffersCallback() {
                @Override
                public void onOffersLoaded(List<Offer> offers) {
                    refreshCache(offers);
                    callback.onOffersLoaded(new ArrayList<>(mCachedOffers.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getOffersFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void getOffers(final String offerTypeId, final LoadOffersCallback callback) {
        // Respond immediately with cache if available and not dirty
        if (mCachedOffers != null && !mCacheIsDirty) {
            callback.onOffersLoaded(new ArrayList<>(mCachedOffers.values()));
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getOffersFromRemoteDataSource(offerTypeId, callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mOffersLocalDataSource.getOffers(offerTypeId, new LoadOffersCallback() {
                @Override
                public void onOffersLoaded(List<Offer> offers) {
                    refreshCache(offers);
                    callback.onOffersLoaded(new ArrayList<>(mCachedOffers.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getOffersFromRemoteDataSource(offerTypeId, callback);
                }
            });
        }
    }

    @Override
    public void saveOffer(Offer offer) {
        mOffersRemoteDataSource.saveOffer(offer);
        mOffersLocalDataSource.saveOffer(offer);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedOffers == null) {
            mCachedOffers = new LinkedHashMap<>();
        }
        mCachedOffers.put(offer.getId(), offer);
    }

    @Override
    public void creditedOffer(Offer offer) {
        mOffersRemoteDataSource.creditedOffer(offer);
        mOffersLocalDataSource.creditedOffer(offer);

//        offer.setCredited(true);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedOffers == null) {
            mCachedOffers = new LinkedHashMap<>();
        }
        mCachedOffers.put(offer.getId(), offer);
    }

    @Override
    public void creditedOffer(String offerId) {
        creditedOffer(getOfferWithId(offerId));
    }

    @Override
    public void getOffer(final String offerId, final GetOfferCallback callback) {
        Offer cachedOffer = getOfferWithId(offerId);

        // Respond immediately with cache if available
        if (cachedOffer != null) {
            callback.onOfferLoaded(cachedOffer);
            return;
        }

        // Load from server/persisted if needed.

        // Is the offer in the local data source? If not, query the network.
        mOffersLocalDataSource.getOffer(offerId, new GetOfferCallback() {
            @Override
            public void onOfferLoaded(Offer offer) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedOffers == null) {
                    mCachedOffers = new LinkedHashMap<>();
                }
                mCachedOffers.put(offer.getId(), offer);
                callback.onOfferLoaded(offer);
            }

            @Override
            public void onDataNotAvailable() {
                mOffersRemoteDataSource.getOffer(offerId, new GetOfferCallback() {
                    @Override
                    public void onOfferLoaded(Offer offer) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedOffers == null) {
                            mCachedOffers = new LinkedHashMap<>();
                        }
                        mCachedOffers.put(offer.getId(), offer);
                        callback.onOfferLoaded(offer);
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
    public void refreshOffers() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllOffers() {
        mOffersRemoteDataSource.deleteAllOffers();
        mOffersLocalDataSource.deleteAllOffers();

        if (mCachedOffers == null) {
            mCachedOffers = new LinkedHashMap<>();
        }
        mCachedOffers.clear();
    }

    @Override
    public void deleteOffer(String offerId) {
        mOffersRemoteDataSource.deleteOffer(offerId);
        mOffersLocalDataSource.deleteOffer(offerId);

        mCachedOffers.remove(offerId);
    }

    @Override
    public int numOffers() {
        return mOffersLocalDataSource.numOffers();
    }

    private void getOffersFromRemoteDataSource(final LoadOffersCallback callback) {
        mOffersRemoteDataSource.getOffers(new LoadOffersCallback() {
            @Override
            public void onOffersLoaded(List<Offer> offers) {
                refreshCache(offers);
                refreshLocalDataSource(offers);
                callback.onOffersLoaded(new ArrayList<>(mCachedOffers.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void getOffersFromRemoteDataSource(String offerTypeId, final LoadOffersCallback callback) {
        mOffersRemoteDataSource.getOffers(offerTypeId, new LoadOffersCallback() {
            @Override
            public void onOffersLoaded(List<Offer> offers) {
                refreshCache(offers);
                refreshLocalDataSource(offers);
                callback.onOffersLoaded(new ArrayList<>(mCachedOffers.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Offer> offers) {
        if (mCachedOffers == null) {
            mCachedOffers = new LinkedHashMap<>();
        }
        mCachedOffers.clear();
        for (Offer offer : offers) {
            mCachedOffers.put(offer.getId(), offer);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Offer> offers) {
        mOffersLocalDataSource.deleteAllOffers();
        for (Offer offer : offers) {
            mOffersLocalDataSource.saveOffer(offer);
        }
    }

    private Offer getOfferWithId(String id) {
        if (mCachedOffers == null || mCachedOffers.isEmpty()) return null;
        else return mCachedOffers.get(id);
    }
}
