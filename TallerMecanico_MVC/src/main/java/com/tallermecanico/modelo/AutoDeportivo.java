package com.tallermecanico.modelo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Modelo de Auto Deportivo Japonés para JDM Tuning Shop.
 */
public class AutoDeportivo {

    public enum MarcaJDM {
        NISSAN, TOYOTA, MAZDA, HONDA, SUBARU, MITSUBISHI
    }

    private String id;
    private MarcaJDM marca;
    private String modelo;
    private int año;
    private String motor;
    private String patente;
    private String clienteId;
    private int potenciaHP;
    private Map<String, String> modificaciones;

    public AutoDeportivo() {
        this.id = UUID.randomUUID().toString();
        this.modificaciones = new HashMap<>();
        this.potenciaHP = 0;
    }

    public AutoDeportivo(MarcaJDM marca, String modelo, int año, String motor, String patente) {
        this();
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.motor = motor;
        this.patente = patente;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MarcaJDM getMarca() {
        return marca;
    }

    public void setMarca(MarcaJDM marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public int getPotenciaHP() {
        return potenciaHP;
    }

    public void setPotenciaHP(int potenciaHP) {
        this.potenciaHP = potenciaHP;
    }

    public Map<String, String> getModificaciones() {
        return new HashMap<>(modificaciones);
    }

    public void setModificaciones(Map<String, String> modificaciones) {
        this.modificaciones = new HashMap<>(modificaciones);
    }

    public void agregarModificacion(String componente, String detalle) {
        modificaciones.put(componente, detalle);
    }

    public String getNombreCompleto() {
        return marca + " " + modelo + " (" + año + ")";
    }

    @Override
    public String toString() {
        return String.format("Auto[%s %s %d - Motor: %s, %dHP, Mods: %d]",
                marca, modelo, año, motor, potenciaHP, modificaciones.size());
    }
}
