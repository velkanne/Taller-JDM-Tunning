package com.tallermecanico.modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorOrdenes {
    private List<OrdenTrabajo> ordenes;
    private static final String ARCHIVO_DATOS = "ordenes.dat";
    
    public GestorOrdenes() {
        this.ordenes = new ArrayList<>();
        cargarDatos();
    }
    
    public void agregarOrden(OrdenTrabajo orden) {
        if (orden != null) {
            ordenes.add(orden);
        }
    }
    
    public void eliminarOrden(int indice) {
        if (indice >= 0 && indice < ordenes.size()) {
            ordenes.remove(indice);
        }
    }
    
    public List<OrdenTrabajo> obtenerOrdenes() {
        return new ArrayList<>(ordenes);
    }
    
    public OrdenTrabajo obtenerOrden(int indice) {
        if (indice >= 0 && indice < ordenes.size()) {
            return ordenes.get(indice);
        }
        return null;
    }
    
    public int getCantidadOrdenes() {
        return ordenes.size();
    }
    
    public void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_DATOS))) {
            oos.writeObject(ordenes);
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(ARCHIVO_DATOS))) {
                ordenes = (List<OrdenTrabajo>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar datos: " + e.getMessage());
                ordenes = new ArrayList<>();
            }
        }
    }
}
