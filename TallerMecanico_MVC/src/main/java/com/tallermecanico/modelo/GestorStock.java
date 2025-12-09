package com.tallermecanico.modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Gestor de Stock de Piezas con control de inventario.
 */
public class GestorStock {
    private static final Logger logger = LoggerFactory.getLogger(GestorStock.class);
    private List<StockPieza> stock;
    private static final String ARCHIVO_DATOS = "stock.json";
    private final Gson gson;

    public GestorStock() {
        this.stock = new ArrayList<>();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        logger.info("Inicializando GestorStock");
        cargarDatos();
    }

    public void agregarStock(StockPieza stockPieza) {
        if (stockPieza != null) {
            stock.add(stockPieza);
            logger.debug("Stock agregado para pieza: {}", stockPieza.getPiezaId());
        }
    }

    public void actualizarStock(String piezaId, int nuevaCantidad) {
        stock.stream()
                .filter(s -> s.getPiezaId().equals(piezaId))
                .findFirst()
                .ifPresent(s -> s.setCantidad(nuevaCantidad));
    }

    public boolean agregarCantidad(String piezaId, int cantidad) {
        StockPieza stockItem = obtenerStock(piezaId);
        if (stockItem != null) {
            stockItem.agregarStock(cantidad);
            logger.info("Stock agregado: {} unidades de pieza {}", cantidad, piezaId);
            return true;
        }
        return false;
    }

    public boolean retirarCantidad(String piezaId, int cantidad) {
        StockPieza stockItem = obtenerStock(piezaId);
        if (stockItem != null) {
            boolean exito = stockItem.retirarStock(cantidad);
            if (exito) {
                logger.info("Stock retirado: {} unidades de pieza {}", cantidad, piezaId);
                if (stockItem.alertaBajoStock()) {
                    logger.warn("ALERTA: Stock bajo para pieza {}", piezaId);
                }
            }
            return exito;
        }
        return false;
    }

    public StockPieza obtenerStock(String piezaId) {
        return stock.stream()
                .filter(s -> s.getPiezaId().equals(piezaId))
                .findFirst()
                .orElse(null);
    }

    public List<StockPieza> obtenerTodo() {
        return new ArrayList<>(stock);
    }

    public List<StockPieza> alertasBajoStock() {
        return stock.stream()
                .filter(StockPieza::alertaBajoStock)
                .collect(Collectors.toList());
    }

    public List<StockPieza> alertasSobreStock() {
        return stock.stream()
                .filter(StockPieza::alertaSobreStock)
                .collect(Collectors.toList());
    }

    public int getCantidadTotal(String piezaId) {
        StockPieza stockItem = obtenerStock(piezaId);
        return stockItem != null ? stockItem.getCantidad() : 0;
    }

    public void guardarDatos() {
        try (Writer writer = new FileWriter(ARCHIVO_DATOS)) {
            gson.toJson(stock, writer);
            logger.info("Stock guardado: {} items", stock.size());
        } catch (IOException e) {
            logger.error("Error al guardar stock: {}", e.getMessage(), e);
        }
    }

    private void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (archivo.exists()) {
            try (Reader reader = new FileReader(ARCHIVO_DATOS)) {
                Type tipoLista = new TypeToken<ArrayList<StockPieza>>() {
                }.getType();
                List<StockPieza> stockLeido = gson.fromJson(reader, tipoLista);
                if (stockLeido != null) {
                    stock = stockLeido;
                    logger.info("Stock cargado: {} items", stock.size());
                }
            } catch (IOException e) {
                logger.error("Error al cargar stock: {}", e.getMessage(), e);
            }
        }
    }
}
