package com.navego360.credito.models.credito;

public class Offer {
    private String id;
    private String quota;
    private String creditDate;
    private String tcea;

    private String quotaNoAdjust;
    private String rateGrace;
    private String rateProcess;
    private String monthGrace;
    private String daysProcess;
    private boolean credited;

    private String idDmmCampaign;
    private String idProProspect;
    private String idProOffer;
    private String idProOfferDetail;

    private String offerTypeId;

    /* Get and Set */
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getQuota() {
        return quota;
    }
    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getCreditDate() {
        return creditDate;
    }
    public void setCreditDate(String creditDate) {
        this.creditDate = creditDate;
    }

    public String getTcea() {
        return tcea;
    }
    public void setTcea(String tcea) {
        this.tcea = tcea;
    }

    public String getOfferTypeId() {
        return offerTypeId;
    }
    public void setOfferTypeId(String offerTypeId) {
        this.offerTypeId = offerTypeId;
    }

    public String getQuotaNoAdjust() {
        return quotaNoAdjust;
    }
    public void setQuotaNoAdjust(String quotaNoAdjust) {
        this.quotaNoAdjust = quotaNoAdjust;
    }

    public String getRateGrace() {
        return rateGrace;
    }
    public void setRateGrace(String rateGrace) {
        this.rateGrace = rateGrace;
    }

    public String getRateProcess() {
        return rateProcess;
    }
    public void setRateProcess(String rateProcess) {
        this.rateProcess = rateProcess;
    }

    public String getMonthGrace() {
        return monthGrace;
    }
    public void setMonthGrace(String monthGrace) {
        this.monthGrace = monthGrace;
    }

    public String getDaysProcess() {
        return daysProcess;
    }
    public void setDaysProcess(String daysProcess) {
        this.daysProcess = daysProcess;
    }

    public String getIdDmmCampaign() {
        return idDmmCampaign;
    }
    public void setIdDmmCampaign(String idDmmCampaign) {
        this.idDmmCampaign = idDmmCampaign;
    }

    public String getIdProProspect() {
        return idProProspect;
    }
    public void setIdProProspect(String idProProspect) {
        this.idProProspect = idProProspect;
    }

    public String getIdProOffer() {
        return idProOffer;
    }
    public void setIdProOffer(String idProOffer) {
        this.idProOffer = idProOffer;
    }

    public String getIdProOfferDetail() {
        return idProOfferDetail;
    }
    public void setIdProOfferDetail(String idProOfferDetail) {
        this.idProOfferDetail = idProOfferDetail;
    }

    public boolean isCredited() {
        return credited;
    }
    public void setCredited(boolean credited) {
        this.credited = credited;
    }
}
