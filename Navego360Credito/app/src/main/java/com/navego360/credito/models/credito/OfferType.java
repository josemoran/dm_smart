package com.navego360.credito.models.credito;

public class OfferType {
    private String id;
    private String creditType;
    private String amount;
    private String quota;
    private String creditDate;
    private String rate;
    private String flat;
    private String disgrace;
    private String offerType;
    private String tcea;

    private boolean blocked;
    private boolean credited;

    /* Get and Set */
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCreditType() {
        return creditType;
    }
    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getRate() {
        return rate;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getFlat() {
        return flat;
    }
    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getDisgrace() {
        return disgrace;
    }
    public void setDisgrace(String disgrace) {
        this.disgrace = disgrace;
    }

    public String getOfferType() {
        return offerType;
    }
    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getTcea() {
        return tcea;
    }
    public void setTcea(String tcea) {
        this.tcea = tcea;
    }

    public boolean isBlocked() {
        return blocked;
    }
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isCredited() {
        return credited;
    }
    public void setCredited(boolean credited) {
        this.credited = credited;
    }
}
