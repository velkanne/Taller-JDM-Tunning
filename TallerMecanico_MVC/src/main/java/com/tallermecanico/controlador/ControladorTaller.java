package com.tallermecanico.controlador;

import com.tallermecanico.modelo.GestorOrdenes;
import com.tallermecanico.modelo.OrdenTrabajo;
import com.tallermecanico.util.ValidadorPatente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador principal del sistema de taller mecánico.
 * Maneja la lógica de negocio y validaciones.
 */
public class ControladorTaller {
    private static final Logger logger = LoggerFactory.getLogger(ControladorTaller.class);
    private GestorOrdenes gestorOrdenes;

    public ControladorTaller() {
        this.gestorOrdenes = new GestorOrdenes();
        logger.info("ControladorTaller inicializado");
    }

    /**
     * Agrega una nueva orden de trabajo.
     * 
     * @return true si se agregó correctamente, false si hubo error de validación
     */
    public boolean agregarOrden(String patente, String modeloAuto, String servicio,
            String urgencia, boolean clienteEspera, String observaciones) {

        if (!validarDatos(patente, modeloAuto)) {
            logger.warn("Validación fallida al agregar orden: patente={}, modelo={}", patente, modeloAuto);
            return false;
        }

        OrdenTrabajo nuevaOrden = new OrdenTrabajo(
                ValidadorPatente.normalizar(patente),
                modeloAuto.trim(),
                servicio,
                urgencia,
                clienteEspera,
                observaciones.trim());

        gestorOrdenes.agregarOrden(nuevaOrden);
        gestorOrdenes.guardarDatos();
        logger.info("Orden agregada exitosamente: {}", nuevaOrden.getPatente());
        return true;
    }

    /**
     * Modifica una orden existente.
     * 
     * @param indice Índice de la orden a modificar
     * @return true si se modificó correctamente, false si hubo error
     */
    public boolean modificarOrden(int indice, String patente, String modeloAuto, String servicio,
            String urgencia, boolean clienteEspera, String observaciones) {

        if (!validarDatos(patente, modeloAuto)) {
            return false;
        }

        OrdenTrabajo ordenModificada = new OrdenTrabajo(
                ValidadorPatente.normalizar(patente),
                modeloAuto.trim(),
                servicio,
                urgencia,
                clienteEspera,
                observaciones.trim());

        boolean exito = gestorOrdenes.modificarOrden(indice, ordenModificada);
        if (exito) {
            gestorOrdenes.guardarDatos();
        }
        return exito;
    }

    /**
     * Elimina una orden por índice.
     */
    public void eliminarOrden(int indice) {
        gestorOrdenes.eliminarOrden(indice);
        gestorOrdenes.guardarDatos();
    }

    /**
     * Obtiene todas las órdenes registradas.
     */
    public List<OrdenTrabajo> obtenerTodasLasOrdenes() {
        return gestorOrdenes.obtenerOrdenes();
    }

    /**
     * Obtiene una orden específica por índice.
     */
    public OrdenTrabajo obtenerOrden(int indice) {
        return gestorOrdenes.obtenerOrden(indice);
    }

    /**
     * Busca órdenes por patente.
     * 
     * @param patente Patente o fragmento a buscar
     * @return Lista de órdenes que coinciden
     */
    public List<OrdenTrabajo> buscarPorPatente(String patente) {
        return gestorOrdenes.buscarPorPatente(patente);
    }

    /**
     * Filtra órdenes por nivel de urgencia.
     * 
     * @param urgencia Nivel de urgencia ("Normal", "Urgente", "Crítico", "Todas")
     * @return Lista de órdenes filtradas
     */
    public List<OrdenTrabajo> filtrarPorUrgencia(String urgencia) {
        return gestorOrdenes.filtrarPorUrgencia(urgencia);
    }

    /**
     * Filtra órdenes por tipo de servicio.
     * 
     * @param servicio Tipo de servicio ("Mantención", "Frenos", "Aceite", "Motor",
     *                 "Todos")
     * @return Lista de órdenes filtradas
     */
    public List<OrdenTrabajo> filtrarPorServicio(String servicio) {
        return gestorOrdenes.filtrarPorServicio(servicio);
    }

    /**
     * Obtiene cantidad total de órdenes.
     */
    public int getCantidadOrdenes() {
        return gestorOrdenes.getCantidadOrdenes();
    }

    /**
     * Obtiene estadísticas completas del taller.
     * 
     * @return Map con contadores (total, normal, urgente, critico, espera)
     */
    public Map<String, Integer> obtenerEstadisticas() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("total", gestorOrdenes.getCantidadOrdenes());
        stats.put("normal", gestorOrdenes.contarPorUrgencia("Normal"));
        stats.put("urgente", gestorOrdenes.contarPorUrgencia("Urgente"));
        stats.put("critico", gestorOrdenes.contarPorUrgencia("Crítico"));
        stats.put("espera", gestorOrdenes.contarClientesEspera());
        return stats;
    }

    /**
     * Valida los datos de una orden de trabajo.
     */
    private boolean validarDatos(String patente, String modeloAuto) {
        if (patente == null || patente.trim().isEmpty()) {
            logger.debug("Validación fallida: patente vacía");
            mostrarError("La patente no puede estar vacía");
            return false;
        }

        if (modeloAuto == null || modeloAuto.trim().isEmpty()) {
            logger.debug("Validación fallida: modelo vacío");
            mostrarError("El modelo del auto no puede estar vacío");
            return false;
        }

        if (!ValidadorPatente.validar(patente.trim())) {
            logger.debug("Validación fallida: formato patente inválido - {}", patente);
            mostrarError("Formato de patente inválido.\n\n" + ValidadorPatente.getMensajeFormato());
            return false;
        }

        return true;
    }

    /**
     * Muestra mensaje de error al usuario.
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error de Validación",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Guarda datos manualmente.
     */
    public void guardarDatos() {
        gestorOrdenes.guardarDatos();
    }
}
