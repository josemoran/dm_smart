package com.navego360.credito.models.credito;

public class UserInfo {
    private String id; // Id de form data 'Oferta'
    private String userName;
    private String document;
    private String userEmail;
    private String expiredDate;//Feha de expiracion
    private String lastAmount; // ultimo monto
    private String maxOfferAmount; // Oferta maxima
    private String realAmount; // Saldo a desembolsar
    private String borrowCapacity; //Endeudamiento
    private String disbursement; // Forma de desembolso
    private String approvedDate; //Fecha de aprobacion
    private String discount; // Forma de descuento

    private int serviceId; // Id de servicio

    private boolean credited;

    /* Get y Set */

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document;
    }

    public String getExpiredDate() {
        return expiredDate;
    }
    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getLastAmount() {
        return lastAmount;
    }
    public void setLastAmount(String lastAmount) {
        this.lastAmount = lastAmount;
    }

    public String getMaxOfferAmount() {
        return maxOfferAmount;
    }
    public void setMaxOfferAmount(String maxOfferAmount) {
        this.maxOfferAmount = maxOfferAmount;
    }

    public String getRealAmount() {
        return realAmount;
    }
    public void setRealAmount(String realAmount) {
        this.realAmount = realAmount;
    }

    public String getBorrowCapacity() {
        return borrowCapacity;
    }
    public void setBorrowCapacity(String borrowCapacity) {
        this.borrowCapacity = borrowCapacity;
    }

    public String getDisbursement() {
        return disbursement;
    }
    public void setDisbursement(String disbursement) {
        this.disbursement = disbursement;
    }

    public boolean isCredited() {
        return credited;
    }
    public void setCredited(boolean credited) {
        this.credited = credited;
    }

    public String getApprovedDate() {
        return approvedDate;
    }
    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getDiscount() {
        return discount;
    }
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getServiceId() {
        return serviceId;
    }
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
