package com.tallermecanico.modelo;

import java.time.LocalDate;

/**
 * Control de Stock de Piezas para JDM Shop.
 */
public class StockPieza {

    private String piezaId;
    private int cantidad;
    private int cantidadMinima;
    private int cantidadMaxima;
    private String ubicacion;
    private LocalDate ultimaActualizacion;

    public StockPieza() {
        this.cantidad = 0;
        this.cantidadMinima = 5;
        this.cantidadMaxima = 100;
        this.ubicacion = "ALMACEN";
        this.ultimaActualizacion = LocalDate.now();
    }

    public StockPieza(String piezaId, int cantidad, int cantidadMinima, String ubicacion) {
        this();
        this.piezaId = piezaId;
        this.cantidad = cantidad;
        this.cantidadMinima = cantidadMinima;
        this.ubicacion = ubicacion;
    }

    // Getters y Setters
    public String getPiezaId() {
        return piezaId;
    }

    public void setPiezaId(String piezaId) {
        this.piezaId = piezaId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.ultimaActualizacion = LocalDate.now();
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public int getCantidadMaxima() {
        return cantidadMaxima;
    }

    public void setCantidadMaxima(int cantidadMaxima) {
        this.cantidadMaxima = cantidadMaxima;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public LocalDate getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(LocalDate ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    // Métodos de negocio
    public boolean alertaBajoStock() {
        return cantidad <= cantidadMinima;
    }

    public boolean alertaSobreStock() {
        return cantidad >= cantidadMaxima;
    }

    public void agregarStock(int cant) {
        this.cantidad += cant;
        this.ultimaActualizacion = LocalDate.now();
    }

    public boolean retirarStock(int cant) {
        if (cantidad >= cant) {
            this.cantidad -= cant;
            this.ultimaActualizacion = LocalDate.now();
            return true;
        }
        return false;
    }

    public String getEstadoStock() {
        if (alertaBajoStock())
            return "BAJO";
        if (alertaSobreStock())
            return "ALTO";
        return "NORMAL";
    }

    @Override
    public String toString() {
        return String.format("Stock[Pieza=%s, Cant=%d, Estado=%s, Ubicacion=%s]",
                piezaId.substring(0, 8), cantidad, getEstadoStock(), ubicacion);
    }
}
