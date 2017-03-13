package com.navego360.credito.models.navego;

public class FormData {
    private int formDataId;
    private int formId;
    private int serviceId;
    private String label;
    private String efs;
    private int operation;
    private int syncWeb;

    /* Constructor */
    public FormData(){}

    /* Get and Set */
    public int getFormDataId() {
        return formDataId;
    }

    public void setFormDataId(int formDataId) {
        this.formDataId = formDataId;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getEfs() {
        return efs;
    }

    public void setEfs(String efs) {
        this.efs = efs;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public int getSyncWeb() {
        return syncWeb;
    }

    public void setSyncWeb(int syncWeb) {
        this.syncWeb = syncWeb;
    }

    @Override
    public String toString(){
        return "formDataId: " + formDataId + "|formId: " + formId
                + "|serviceId: " + serviceId + "|label: " + label + "|efs: " + efs
                + "|operation: " + operation + "|syncWeb: " + syncWeb;
    }
}
