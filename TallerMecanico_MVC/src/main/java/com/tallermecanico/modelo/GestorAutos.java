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
 * Gestor de Autos Deportivos JDM con catálogo pre-cargado.
 */
public class GestorAutos {
    private static final Logger logger = LoggerFactory.getLogger(GestorAutos.class);
    private List<AutoDeportivo> autos;
    private static final String ARCHIVO_DATOS = "autos.json";
    private final Gson gson;

    public GestorAutos() {
        this.autos = new ArrayList<>();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        logger.info("Inicializando GestorAutos");
        cargarDatos();
        if (autos.isEmpty()) {
            cargarCatalogoJDM();
        }
    }

    public void agregarAuto(AutoDeportivo auto) {
        if (auto != null) {
            autos.add(auto);
            logger.debug("Auto agregado: {}", auto.getNombreCompleto());
        }
    }

    public boolean modificarAuto(String id, AutoDeportivo autoActualizado) {
        for (int i = 0; i < autos.size(); i++) {
            if (autos.get(i).getId().equals(id)) {
                autos.set(i, autoActualizado);
                return true;
            }
        }
        return false;
    }

    public void eliminarAuto(String id) {
        autos.removeIf(a -> a.getId().equals(id));
    }

    public AutoDeportivo obtenerAuto(String id) {
        return autos.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<AutoDeportivo> obtenerTodos() {
        return new ArrayList<>(autos);
    }

    public List<AutoDeportivo> filtrarPorMarca(AutoDeportivo.MarcaJDM marca) {
        return autos.stream()
                .filter(a -> a.getMarca() == marca)
                .collect(Collectors.toList());
    }

    public List<AutoDeportivo> filtrarPorCliente(String clienteId) {
        return autos.stream()
                .filter(a -> clienteId.equals(a.getClienteId()))
                .collect(Collectors.toList());
    }

    public List<AutoDeportivo> buscarPorModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            return new ArrayList<>(autos);
        }
        String busqueda = modelo.trim().toLowerCase();
        return autos.stream()
                .filter(a -> a.getModelo().toLowerCase().contains(busqueda))
                .collect(Collectors.toList());
    }

    public int getCantidad() {
        return autos.size();
    }

    /**
     * Carga catálogo de autos JDM icónicos.
     */
    private void cargarCatalogoJDM() {
        logger.info("Cargando catálogo JDM iconic cars...");
        
        // Nissan Skyline GT-R
        AutoDeportivo skylineR34 = new Auto Deportivo(AutoDeportivo.MarcaJDM.NISSAN, "Skyline GT-R R34", 1999, "RB26DETT", "DEMO-001");
        skylineR34.setPotenciaHP(280);
        autos.add(skylineR34);
        
        // Toyota Supra
        AutoDeportivo supra = new AutoDeportivo(AutoDeportivo.MarcaJDM.TOYOTA, "Supra A80", 1993, "2JZ-GTE", "DEMO-002");
        supra.setPotenciaHP(320);
        autos.add(supra);
        
        // Mazda RX-7
        AutoDeportivo rx7 = new AutoDeportivo(AutoDeportivo.MarcaJDM.MAZDA, "RX-7 FD", 1993, "13B-REW", "DEMO-003");
        rx7.setPotenciaHP(255);
        autos.add(rx7);
        
        // Honda NSX
        AutoDeportivo nsx = new AutoDeportivo(AutoDeportivo.MarcaJDM.HONDA, "NSX NA1", 1990, "C30A", "DEMO-004");
        nsx.setPotenciaHP(270);
        autos.add(nsx);
        
        // Subaru WRX STI
        AutoDeportivo wrx = new AutoDeportivo(AutoDeportivo.MarcaJDM.SUBARU, "WRX STI", 2004, "EJ257", "DEMO-005");
        wrx.setPotenciaHP(300);
        autos.add(wrx);
        
        // Mitsubishi Lancer Evolution
        AutoDeportivo evo = new AutoDeportivo(AutoDeportivo.MarcaJDM.MITSUBISHI, "Lancer Evolution IX", 2005, "4G63T", "DEMO-006");
        evo.setPotenciaHP(286);
        autos.add(evo);
        
        logger.info("Catálogo JDM cargado: {} autos icónicos", autos.size());
        guardarDatos();
    }

    public void guardarDatos() {
        try (Writer writer = new FileWriter(ARCHIVO_DATOS)) {
            gson.toJson(autos, writer);
            logger.info("Autos guardados: {} registros", autos.size());
        } catch (IOException e) {
            logger.error("Error al guardar autos: {}", e.getMessage(), e);
        }
    }

    private void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (archivo.exists()) {
            try (Reader reader = new FileReader(ARCHIVO_DATOS)) {
                Type tipoLista = new TypeToken<ArrayList<AutoDeportivo>>() {
                }.getType();
                List<AutoDeportivo> autosLeidos = gson.fromJson(reader, tipoLista);
                if (autosLeidos != null) {
                    autos = autosLeidos;
                    logger.info("Autos cargados: {}", autos.size());
                }
            } catch (IOException e) {
                logger.error("Error al cargar autos: {}", e.getMessage(), e);
            }
        }
    }
}
