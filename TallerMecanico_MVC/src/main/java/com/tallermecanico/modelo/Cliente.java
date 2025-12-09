package com.tallermecanico.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Modelo de Cliente para JDM Tuning Shop.
 */
public class Cliente {

    public enum TipoCliente {
        REGULAR, VIP, CORPORATIVO
    }

    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private LocalDate fechaRegistro;
    private TipoCliente tipo;
    private List<String> autosIds;
    private List<String> ordenesIds;

    public Cliente() {
        this.id = UUID.randomUUID().toString();
        this.fechaRegistro = LocalDate.now();
        this.tipo = TipoCliente.REGULAR;
        this.autosIds = new ArrayList<>();
        this.ordenesIds = new ArrayList<>();
    }

    public Cliente(String nombre, String email, String telefono, TipoCliente tipo) {
        this();
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    public List<String> getAutosIds() {
        return new ArrayList<>(autosIds);
    }

    public void setAutosIds(List<String> autosIds) {
        this.autosIds = new ArrayList<>(autosIds);
    }

    public void agregarAuto(String autoId) {
        if (!autosIds.contains(autoId)) {
            autosIds.add(autoId);
        }
    }

    public List<String> getOrdenesIds() {
        return new ArrayList<>(ordenesIds);
    }

    public void setOrdenesIds(List<String> ordenesIds) {
        this.ordenesIds = new ArrayList<>(ordenesIds);
    }

    public void agregarOrden(String ordenId) {
        if (!ordenesIds.contains(ordenId)) {
            ordenesIds.add(ordenId);
        }
    }

    @Override
    public String toString() {
        return String.format("Cliente[ID=%s, Nombre=%s, Tipo=%s, Autos=%d, Ordenes=%d]",
                id.substring(0, 8), nombre, tipo, autosIds.size(), ordenesIds.size());
    }
}
