package com.tallermecanico.controlador;

import com.tallermecanico.modelo.GestorOrdenes;
import com.tallermecanico.modelo.OrdenTrabajo;

import javax.swing.JOptionPane;
import java.util.List;

public class ControladorTaller {
    private GestorOrdenes gestorOrdenes;

    public ControladorTaller() {
        this.gestorOrdenes = new GestorOrdenes();
    }

    public boolean agregarOrden(String patente, String modeloAuto, String servicio,
            String urgencia, boolean clienteEspera, String observaciones) {

        if (!validarDatos(patente, modeloAuto, observaciones)) {
            return false;
        }

        OrdenTrabajo nuevaOrden = new OrdenTrabajo(
                patente.trim().toUpperCase(),
                modeloAuto.trim(),
                servicio,
                urgencia,
                clienteEspera,
                observaciones.trim());

        gestorOrdenes.agregarOrden(nuevaOrden);
        gestorOrdenes.guardarDatos();
        return true;
    }

    public void eliminarOrden(int indice) {
        gestorOrdenes.eliminarOrden(indice);
        gestorOrdenes.guardarDatos();
    }

    public List<OrdenTrabajo> obtenerTodasLasOrdenes() {
        return gestorOrdenes.obtenerOrdenes();
    }

    public OrdenTrabajo obtenerOrden(int indice) {
        return gestorOrdenes.obtenerOrden(indice);
    }

    public int getCantidadOrdenes() {
        return gestorOrdenes.getCantidadOrdenes();
    }

    private boolean validarDatos(String patente, String modeloAuto, String observaciones) {
        if (patente == null || patente.trim().isEmpty()) {
            mostrarError("La patente no puede estar vacía");
            return false;
        }

        if (modeloAuto == null || modeloAuto.trim().isEmpty()) {
            mostrarError("El modelo del auto no puede estar vacío");
            return false;
        }

        if (patente.trim().length() < 5 || patente.trim().length() > 7) {
            mostrarError("La patente debe tener entre 5 y 7 caracteres");
            return false;
        }

        return true;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error de Validación",
                JOptionPane.ERROR_MESSAGE);
    }

    public void guardarDatos() {
        gestorOrdenes.guardarDatos();
    }
}
