package com.navego360.credito.models.navego;

public class FormAnswer {
    private int formDataId;
    private String componentId;
    private String answer;
    private int typeComponent;
    private int operation;
    private int syncWeb;

    private int serviceId;

    /* Constructor */
    public FormAnswer(){}

    /* Get and Set */
    public int getFormDataId() {
        return formDataId;
    }

    public void setFormDataId(int formDataId) {
        this.formDataId = formDataId;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getTypeComponent() {
        return typeComponent;
    }

    public void setTypeComponent(int typeComponent) {
        this.typeComponent = typeComponent;
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

    public void setSyncWeb(int synWeb) {
        this.syncWeb = synWeb;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString(){
        return "formDataId: " + formDataId + "|componentId: " + componentId + "|answer: " + answer
                + "|typeComponent: " + typeComponent + "|operation: " + operation
                + "|syncWeb: " + syncWeb + "|serviceId: " + serviceId;
    }
}
