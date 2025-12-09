package com.tallermecanico.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tallermecanico.modelo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Gestor de Import/Export JSON para backup y restauración de datos.
 */
public class ImportExportManager {
    private static final Logger logger = LoggerFactory.getLogger(ImportExportManager.class);
    private final Gson gson;

    public ImportExportManager() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    /**
     * Exporta toda la base de datos a un archivo JSON unificado.
     */
    public boolean exportarTodo(String archivoDestino,
            GestorClientes gestorClientes,
            GestorAutos gestorAutos,
            GestorPiezas gestorPiezas,
            GestorStock gestorStock,
            GestorOrdenes gestorOrdenes) {
        try {
            Map<String, Object> baseDatos = new HashMap<>();
            baseDatos.put("clientes", gestorClientes.obtenerTodos());
            baseDatos.put("autos", gestorAutos.obtenerTodos());
            baseDatos.put("piezas", gestorPiezas.obtenerTodas());
            baseDatos.put("stock", gestorStock.obtenerTodo());
            baseDatos.put("ordenes", gestorOrdenes.obtenerOrdenes());
            baseDatos.put("exportDate", LocalDate.now().toString());
            baseDatos.put("version", "3.0-JDM");

            try (Writer writer = new FileWriter(archivoDestino)) {
                gson.toJson(baseDatos, writer);
                logger.info("Base de datos exportada exitosamente a: {}", archivoDestino);
                return true;
            }
        } catch (IOException e) {
            logger.error("Error al exportar base de datos: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Exporta solo un conjunto seleccionado de entidades.
     */
    public boolean exportarParcial(String archivoDestino, Map<String, List<?>> entidades) {
        try (Writer writer = new FileWriter(archivoDestino)) {
            Map<String, Object> exportData = new HashMap<>(entidades);
            exportData.put("exportDate", LocalDate.now().toString());
            exportData.put("exportType", "PARCIAL");

            gson.toJson(exportData, writer);
            logger.info("Export parcial completado: {}", archivoDestino);
            return true;
        } catch (IOException e) {
            logger.error("Error en export parcial: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Lee un archivo de exportación JSON y retorna la estructura.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> leerExportacion(String archivoOrigen) {
        try (Reader reader = new FileReader(archivoOrigen)) {
            Map<String, Object> datos = gson.fromJson(reader, Map.class);
            logger.info("Archivo de exportación leído: {}", archivoOrigen);
            return datos;
        } catch (IOException e) {
            logger.error("Error al leer exportación: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Valida estructura de archivo de exportación.
     */
    public boolean validarExportacion(Map<String, Object> datos) {
        if (datos == null)
            return false;

        boolean esValido = datos.containsKey("exportDate") && dados.containsKey("version");

        if (esValido) {
            logger.info("Exportación válida - Version: {}, Fecha: {}",
                    datos.get("version"), datos.get("exportDate"));
        }

        return esValido;
    }

    /**
     * Genera nombre de archivo para backup automático.
     */
    public String generarNombreBackup() {
        return String.format("backup_jdm_%s.json",
                LocalDate.now().toString().replace("-", ""));
    }
}
