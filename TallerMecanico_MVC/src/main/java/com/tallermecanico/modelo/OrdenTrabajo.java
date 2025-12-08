package com.tallermecanico.modelo;

import java.io.Serializable;

public class OrdenTrabajo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String patente;
    private String modeloAuto;
    private String servicio;
    private String urgencia;
    private boolean clienteEspera;
    private String observaciones;
    
    public OrdenTrabajo(String patente, String modeloAuto, String servicio, 
                        String urgencia, boolean clienteEspera, String observaciones) {
        this.patente = patente;
        this.modeloAuto = modeloAuto;
        this.servicio = servicio;
        this.urgencia = urgencia;
        this.clienteEspera = clienteEspera;
        this.observaciones = observaciones;
    }
    
    public OrdenTrabajo() {
        this("", "", "Mantención", "Normal", false, "");
    }
    
    public String getPatente() {
        return patente;
    }
    
    public void setPatente(String patente) {
        this.patente = patente;
    }
    
    public String getModeloAuto() {
        return modeloAuto;
    }
    
    public void setModeloAuto(String modeloAuto) {
        this.modeloAuto = modeloAuto;
    }
    
    public String getServicio() {
        return servicio;
    }
    
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
    
    public String getUrgencia() {
        return urgencia;
    }
    
    public void setUrgencia(String urgencia) {
        this.urgencia = urgencia;
    }
    
    public boolean isClienteEspera() {
        return clienteEspera;
    }
    
    public void setClienteEspera(boolean clienteEspera) {
        this.clienteEspera = clienteEspera;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    @Override
    public String toString() {
        return String.format("Orden[Patente=%s, Modelo=%s, Servicio=%s, Urgencia=%s, Espera=%s]",
                patente, modeloAuto, servicio, urgencia, clienteEspera ? "Sí" : "No");
    }
}
