package com.navego360.credito.ui.offers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.navego360.credito.data.common.local.CreditoRepository;
import com.navego360.credito.data.offer.OffersDataSource;
import com.navego360.credito.data.offer.OffersRepository;
import com.navego360.credito.data.offertype.OfferTypesDataSource;
import com.navego360.credito.data.offertype.OfferTypesRepository;
import com.navego360.credito.data.userinfo.UserInfoDataSource;
import com.navego360.credito.data.userinfo.UserInfoRepository;
import com.navego360.credito.models.credito.Offer;
import com.navego360.credito.models.credito.OfferType;
import com.navego360.credito.models.credito.UserInfo;
import com.navego360.credito.utils.DateUtils;
import com.navego360.credito.utils.DocumentUtils;
import com.navego360.credito.utils.OfferUtils;
import com.navego360.credito.utils.PrintUtils;
import com.navego360.credito.utils.SaveNavegoUtils;
import com.navego360.credito.widgets.ToastMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.navego360.credito.variables.Constants.SEND_MAIL_CODE;

public class OffersPresenter implements OffersContract.Presenter {

    private final OffersContract.View mOffersView;
    private final OffersRepository mOffersRepository;
    private final OfferTypesRepository mOfferTypesRepository;

    private final String mOfferTypeId;

    private UserInfo mUserInfo;
    private OfferType mOfferType;

    private final CreditoRepository mCreditoRepository;

    private Context mContext;

    public OffersPresenter(Context context, String offerTypeId, CreditoRepository creditoRepository,
                           OffersContract.View offerView) {
        mContext = context;
        mOfferTypeId = offerTypeId;
        mCreditoRepository = creditoRepository;
        mOffersRepository = creditoRepository.getOffersRepository();
        mOfferTypesRepository = creditoRepository.getOfferTypesRepository();
        mOffersView = offerView;
        mOffersView.setPresenter(this);
    }

    @Override
    public void start() {
        loadUserInfo();
        loadOffers(mOfferTypeId);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if(requestCode == SEND_MAIL_CODE){
            if(resultCode == RESULT_OK){
                mOffersView.showCompleteMailDialog();
            }
        }
    }

    @Override
    public void loadUserInfo() {
        UserInfoRepository userInfoRepository = mCreditoRepository.getUserInfoRepository();
        userInfoRepository.getUserInfo(new UserInfoDataSource.GetUserInfoCallback() {
            @Override
            public void onUserInfoLoaded(UserInfo userInfo) {
                mUserInfo = userInfo;
                showOfferTypesInfo(userInfo);
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mOffersView.isActive()) {
                    return;
                }
                mOffersView.showLoadingUserInfoError();
            }
        });
    }

    @Override
    public void loadOffers(String offerTypeId) {
        mOfferTypesRepository.getOfferType(offerTypeId, new OfferTypesDataSource.GetOfferTypeCallback() {
            @Override
            public void onOfferTypeLoaded(OfferType offerType) {
                // The view may not be able to handle UI updates anymore
                if (!mOffersView.isActive()) {
                    return;
                }

                if (null == offerType) mOffersView.showMissingOffer();
                else {
                    mOfferType = offerType;
                    showOfferDetail(offerType);
                    lockView(offerType.isCredited());
                }
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mOffersView.isActive()) {
                    return;
                }
                mOffersView.showLoadingOffersError();
            }
        });
    }

    @Override
    public void generateCredit(String disbursementOption, Offer offer) {

        // Localmente
        mOffersRepository.creditedOffer(offer);
        mOfferTypesRepository.creditedOfferType(offer.getOfferTypeId());
        mOfferTypesRepository.blockAllExceptOfferType(offer.getOfferTypeId());
        mCreditoRepository.getUserInfoRepository().saveUserData(mUserInfo, disbursementOption,
                OfferUtils.getCreditDate(), offer.getIdProOfferDetail(),
                OfferUtils.getDeviceImei(mContext));

        // Navego
        SaveNavegoUtils.saveOfferDetail(mContext, mUserInfo, offer);
        SaveNavegoUtils.saveOffer(mContext, mUserInfo, offer);
        SaveNavegoUtils.saveService(mContext, mUserInfo.getServiceId());
    }

    @Override
    public void printCreditedOffer(String disbursementOptions, Offer offer) {
        try {
            Date now = new Date();
            String nowFormat = DateUtils.convertDate(now, DateUtils.formatDate4);
            List<byte[]> data = DocumentUtils.secondDocument(false, mUserInfo.getMaxOfferAmount(),
                    offer.getCreditDate(),nowFormat, mOfferType.getFlat(), offer.getTcea(),
                    mOfferType.getCreditType(),disbursementOptions, offer.getTcea(),
                    mOfferType.getDisgrace(), mUserInfo.getUserName(),mUserInfo.getDocument());
            PrintUtils.printDocument(mContext, data);
            mOffersView.closePrintDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmailCredit(String disbursementOptions, Offer offer, String email) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "Titulo de prueba");
        i.putExtra(Intent.EXTRA_TEXT   , "Probando");
        try {
            ((Activity)mContext).startActivityForResult(
                    Intent.createChooser(i, "Enviar correo ..."), SEND_MAIL_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            ToastMessage.showMessage(mContext, "No se pudo mandar el correo");
        }
    }

    private void showOfferDetail(OfferType offerType){
        mOffersView.showCreditType(mUserInfo.getCreditType());
        mOffersView.showTea(mUserInfo.getTea());
        mOffersView.showFlat(offerType.getFlat());
        mOffersView.showDisgrace(offerType.getDisgrace());
        mOffersView.showAmount(offerType.getAmount());

        double realAmount = Double.parseDouble(offerType.getAmount());
        if(mUserInfo.getLastAmount() != null && !mUserInfo.getLastAmount().equals("")) {
            realAmount = realAmount - Double.parseDouble(mUserInfo.getLastAmount());
        }
        mOffersView.showRealAmount(String.valueOf(realAmount));

        mOffersRepository.getOffers(mOfferTypeId, new OffersDataSource.LoadOffersCallback() {
            @Override
            public void onOffersLoaded(List<Offer> offers) {
                List<Offer> offersToShow = new ArrayList<>();

                // We filter the tasks based on the requestType
                for (Offer offer : offers) {
                    offersToShow.add(offer);
                }

                // The view may not be able to handle UI updates anymore
                if (!mOffersView.isActive()) {
                    return;
                }

                processOffers(offersToShow);
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mOffersView.isActive()) {
                    return;
                }
                mOffersView.showLoadingOffersError();
            }
        });
    }

    private void processOffers(List<Offer> offers){
        if (offers.isEmpty()) processEmptyOfferTs();
        else mOffersView.showOffers(offers);
    }

    private void processEmptyOfferTs() {
        mOffersView.showNoOffer();
    }

    private void showOfferTypesInfo(UserInfo userInfo){
        mOffersView.showClientName(userInfo.getUserName());
        mOffersView.showApprovedDate(userInfo.getApprovedDate());
        mOffersView.showDiscount(userInfo.getDiscount());
        mOffersView.showBorrowCapacity(userInfo.getBorrowCapacity());
        mOffersView.showLastAmount(userInfo.getLastAmount());
        mOffersView.setDisbursementEnabled(userInfo.getListDis());
        mOffersView.setDisbursementOption(userInfo.getDisbursement());
    }

    private void lockView(boolean credited){
        if(credited) {
            mOffersView.showReadOnly();
            mOffersView.blockOfferOptions(credited);
        }
    }
}
