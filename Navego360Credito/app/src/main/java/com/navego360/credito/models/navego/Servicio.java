package com.navego360.credito.models.navego;

public class Servicio {
    private int idServicio;
    private String idTipoServicio;
    private int idCaso;
    private String idEstado;
    private String clienteNombre;
    private String clienteAPaterno;
    private String clienteAMaterno;
    private String clienteTelefono;
    private int idMotorizado;
    private int companyId;
    private String formType;
    private String categoriaId;
    private int flagSyncmobile;

    /* Constructor */
    public Servicio(){
    }

    /* Get y Set */
    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(String idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public int getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(int idCaso) {
        this.idCaso = idCaso;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteAPaterno() {
        return clienteAPaterno;
    }

    public void setClienteAPaterno(String clienteAPaterno) {
        this.clienteAPaterno = clienteAPaterno;
    }

    public String getClienteAMaterno() {
        return clienteAMaterno;
    }

    public void setClienteAMaterno(String clienteAMaterno) {
        this.clienteAMaterno = clienteAMaterno;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }

    public int getIdMotorizado() {
        return idMotorizado;
    }

    public void setIdMotorizado(int idMotorizado) {
        this.idMotorizado = idMotorizado;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getFlagSyncmobile() {
        return flagSyncmobile;
    }

    public void setFlagSyncmobile(int flagSyncmobile) {
        this.flagSyncmobile = flagSyncmobile;
    }

    @Override
    public String toString(){
        return "idServicio: " + idServicio + "|idTipoServicio: " + idTipoServicio
                + "|idCaso: " + idCaso + "|idEstado: " + idEstado
                + "|clienteNombre: " + clienteNombre + "|clienteAPaterno: " + clienteAPaterno
                + "|clienteAMaterno: " + clienteAMaterno + "|clienteTelefono: " + clienteTelefono
                + "|companyId: " + companyId + "|formType: " + formType + "|categoriaId: " + categoriaId;
    }
}
