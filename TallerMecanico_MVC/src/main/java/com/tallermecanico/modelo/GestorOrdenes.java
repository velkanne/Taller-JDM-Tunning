package com.tallermecanico.modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gestor de órdenes de trabajo con persistencia JSON.
 */
public class GestorOrdenes {
    private List<OrdenTrabajo> ordenes;
    private static final String ARCHIVO_DATOS = "ordenes.json";
    private final Gson gson;

    public GestorOrdenes() {
        this.ordenes = new ArrayList<>();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        cargarDatos();
    }

    /**
     * Agrega una nueva orden de trabajo.
     * 
     * @param orden Orden a agregar
     */
    public void agregarOrden(OrdenTrabajo orden) {
        if (orden != null) {
            ordenes.add(orden);
        }
    }

    /**
     * Modifica una orden existente en el índice especificado.
     * 
     * @param indice           Índice de la orden a modificar
     * @param ordenActualizada Nueva orden con datos actualizados
     * @return true si se modificó correctamente, false si el índice es inválido
     */
    public boolean modificarOrden(int indice, OrdenTrabajo ordenActualizada) {
        if (indice >= 0 && indice < ordenes.size() && ordenActualizada != null) {
            ordenes.set(indice, ordenActualizada);
            return true;
        }
        return false;
    }

    /**
     * Elimina una orden de trabajo por índice.
     * 
     * @param indice Índice de la orden a eliminar
     */
    public void eliminarOrden(int indice) {
        if (indice >= 0 && indice < ordenes.size()) {
            ordenes.remove(indice);
        }
    }

    /**
     * Obtiene todas las órdenes de trabajo.
     * 
     * @return Copia de la lista de órdenes
     */
    public List<OrdenTrabajo> obtenerOrdenes() {
        return new ArrayList<>(ordenes);
    }

    /**
     * Obtiene una orden específica por índice.
     * 
     * @param indice Índice de la orden
     * @return Orden de trabajo o null si no existe
     */
    public OrdenTrabajo obtenerOrden(int indice) {
        if (indice >= 0 && indice < ordenes.size()) {
            return ordenes.get(indice);
        }
        return null;
    }

    /**
     * Busca órdenes por patente (búsqueda parcial, case-insensitive).
     * 
     * @param patente Patente o parte de patente a buscar
     * @return Lista de órdenes que coinciden
     */
    public List<OrdenTrabajo> buscarPorPatente(String patente) {
        if (patente == null || patente.trim().isEmpty()) {
            return new ArrayList<>(ordenes);
        }

        String busqueda = patente.trim().toUpperCase();
        return ordenes.stream()
                .filter(o -> o.getPatente().toUpperCase().contains(busqueda))
                .collect(Collectors.toList());
    }

    /**
     * Filtra órdenes por nivel de urgencia.
     * 
     * @param urgencia Nivel de urgencia a filtrar
     * @return Lista de órdenes con la urgencia especificada
     */
    public List<OrdenTrabajo> filtrarPorUrgencia(String urgencia) {
        if (urgencia == null || urgencia.trim().isEmpty() || urgencia.equalsIgnoreCase("Todas")) {
            return new ArrayList<>(ordenes);
        }

        return ordenes.stream()
                .filter(o -> o.getUrgencia().equalsIgnoreCase(urgencia))
                .collect(Collectors.toList());
    }

    /**
     * Filtra órdenes por tipo de servicio.
     * 
     * @param servicio Tipo de servicio a filtrar
     * @return Lista de órdenes con el servicio especificado
     */
    public List<OrdenTrabajo> filtrarPorServicio(String servicio) {
        if (servicio == null || servicio.trim().isEmpty() || servicio.equalsIgnoreCase("Todos")) {
            return new ArrayList<>(ordenes);
        }

        return ordenes.stream()
                .filter(o -> o.getServicio().equalsIgnoreCase(servicio))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene cantidad total de órdenes.
     * 
     * @return Número de órdenes registradas
     */
    public int getCantidadOrdenes() {
        return ordenes.size();
    }

    /**
     * Cuenta órdenes por nivel de urgencia.
     * 
     * @param urgencia Nivel de urgencia
     * @return Cantidad de órdenes con esa urgencia
     */
    public int contarPorUrgencia(String urgencia) {
        return (int) ordenes.stream()
                .filter(o -> o.getUrgencia().equalsIgnoreCase(urgencia))
                .count();
    }

    /**
     * Cuenta órdenes donde el cliente está esperando.
     * 
     * @return Cantidad de órdenes con cliente en espera
     */
    public int contarClientesEspera() {
        return (int) ordenes.stream()
                .filter(OrdenTrabajo::isClienteEspera)
                .count();
    }

    /**
     * Guarda las órdenes en archivo JSON.
     */
    public void guardarDatos() {
        try (Writer writer = new FileWriter(ARCHIVO_DATOS)) {
            gson.toJson(ordenes, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar datos en JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga las órdenes desde archivo JSON.
     */
    private void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (archivo.exists()) {
            try (Reader reader = new FileReader(ARCHIVO_DATOS)) {
                Type tipoLista = new TypeToken<ArrayList<OrdenTrabajo>>() {
                }.getType();
                List<OrdenTrabajo> ordenesLeidas = gson.fromJson(reader, tipoLista);

                if (ordenesLeidas != null) {
                    ordenes = ordenesLeidas;
                } else {
                    ordenes = new ArrayList<>();
                }
            } catch (IOException e) {
                System.err.println("Error al cargar datos desde JSON: " + e.getMessage());
                ordenes = new ArrayList<>();
            }
        }
    }
}
