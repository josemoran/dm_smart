package com.navego360.credito.models;

public class Offer {
    private String id;
    private String quota;
    private String creditDate;
    private String tcea;

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
}
