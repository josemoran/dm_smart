package com.navego360.credito.models;

public class UserInfo {
    private String id;
    private String userName;
    private String document;
    private String expiredDate;
    private String userEmail;
    private String lastAmount;
    private String borrowCapacity;

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

    public String getBorrowCapacity() {
        return borrowCapacity;
    }
    public void setBorrowCapacity(String borrowCapacity) {
        this.borrowCapacity = borrowCapacity;
    }
}
