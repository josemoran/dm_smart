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

    private String tcea;//TCEA
    private String tea;//TEA
    private String creditType;//Tipo de credito
    private String listDis;// Lista de opciones de desembolso

    private String idDmmCampaign;
    private String idProProspect;
    private String idProOffer;
    private String idProOfferDetail;

    private String grantDate;
    private String imeiCode;

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

    public String getTcea() {
        return tcea;
    }
    public void setTcea(String tcea) {
        this.tcea = tcea;
    }

    public String getTea() {
        return tea;
    }
    public void setTea(String tea) {
        this.tea = tea;
    }

    public String getCreditType() {
        return creditType;
    }
    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public String getListDis() {
        return listDis;
    }
    public void setListDis(String listDis) {
        this.listDis = listDis;
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

    public String getGrantDate() {
        return grantDate;
    }
    public void setGrantDate(String grantDate) {
        this.grantDate = grantDate;
    }

    public String getImeiCode() {
        return imeiCode;
    }
    public void setImeiCode(String imeiCode) {
        this.imeiCode = imeiCode;
    }

}
