package com.tallermecanico.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Modelo de Pieza de Tuning para JDM Shop.
 */
public class Pieza {

    public enum Categoria {
        MOTOR, TURBO, ESCAPE, SUSPENSION, FRENOS, TRANSMISION, ESTETICA, ELECTRONICA
    }

    private String id;
    private String nombre;
    private String codigo;
    private Categoria categoria;
    private String fabricante;
    private double precio;
    private List<String> autosCompatibles;
    private String descripcion;

    public Pieza() {
        this.id = UUID.randomUUID().toString();
        this.autosCompatibles = new ArrayList<>();
    }

    public Pieza(String nombre, String codigo, Categoria categoria, String fabricante, double precio) {
        this();
        this.nombre = nombre;
        this.codigo = codigo;
        this.categoria = categoria;
        this.fabricante = fabricante;
        this.precio = precio;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public List<String> getAutosCompatibles() {
        return new ArrayList<>(autosCompatibles);
    }

    public void setAutosCompatibles(List<String> autosCompatibles) {
        this.autosCompatibles = new ArrayList<>(autosCompatibles);
    }

    public void agregarAutoCompatible(String modeloAuto) {
        if (!autosCompatibles.contains(modeloAuto)) {
            autosCompatibles.add(modeloAuto);
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean esCompatibleCon(String modeloAuto) {
        return autosCompatibles.isEmpty() || autosCompatibles.contains(modeloAuto);
    }

    @Override
    public String toString() {
        return String.format("Pieza[%s - %s %s - $%.2f - %s]",
                codigo, fabricante, nombre, precio, categoria);
    }
}
