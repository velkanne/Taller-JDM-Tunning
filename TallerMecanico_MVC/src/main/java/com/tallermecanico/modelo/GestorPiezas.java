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
 * Gestor de Piezas de Tuning JDM.
 */
public class GestorPiezas {
    private static final Logger logger = LoggerFactory.getLogger(GestorPiezas.class);
    private List<Pieza> piezas;
    private static final String ARCHIVO_DATOS = "piezas.json";
    private final Gson gson;

    public GestorPiezas() {
        this.piezas = new ArrayList<>();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        logger.info("Inicializando GestorPiezas");
        cargarDatos();
        if (piezas.isEmpty()) {
            cargarCatalogoPiezasJDM();
        }
    }

    public void agregarPieza(Pieza pieza) {
        if (pieza != null) {
            piezas.add(pieza);
            logger.debug("Pieza agregada: {}", pieza.getNombre());
        }
    }

    public boolean modificarPieza(String id, Pieza piezaActualizada) {
        for (int i = 0; i < piezas.size(); i++) {
            if (piezas.get(i).getId().equals(id)) {
                piezas.set(i, piezaActualizada);
                return true;
            }
        }
        return false;
    }

    public void eliminarPieza(String id) {
        piezas.removeIf(p -> p.getId().equals(id));
    }

    public Pieza obtenerPieza(String id) {
        return piezas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Pieza> obtenerTodas() {
        return new ArrayList<>(piezas);
    }

    public List<Pieza> filtrarPorCategoria(Pieza.Categoria categoria) {
        return piezas.stream()
                .filter(p -> p.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    public List<Pieza> filtrarPorFabricante(String fabricante) {
        return piezas.stream()
                .filter(p -> fabricante.equalsIgnoreCase(p.getFabricante()))
                .collect(Collectors.toList());
    }

    public List<Pieza> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>(piezas);
        }
        String busqueda = nombre.trim().toLowerCase();
        return piezas.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(busqueda))
                .collect(Collectors.toList());
    }

    public int getCantidad() {
        return piezas.size();
    }

    private void cargarCatalogoPiezasJDM() {
        logger.info("Cargando catálogo de piezas JDM...");

        // Turbos
        Pieza turboHKS = new Pieza("Turbo GT3037", "HKS-T3037", Pieza.Categoria.TURBO, "HKS", 2500.00);
        turboHKS.setDescripcion("Turbo premium para motores 2JZ y RB26");
        turboHKS.agregarAutoCompatible("Supra A80");
        turboHKS.agregarAutoCompatible("Skyline GT-R R34");
        piezas.add(turboHKS);

        // Escapes
        Pieza escapeGreddy = new Pieza("Sistema Escape Titanium", "GRD-TI-001", Pieza.Categoria.ESCAPE, "Greddy",
                1800.00);
        escapeGreddy.setDescripcion("Escape completo de titanio con sonido JDM característico");
        piezas.add(escapeGreddy);

        // Suspensión
        Pieza coiloverTein = new Pieza("Coilover Flex Z", "TEIN-FZ", Pieza.Categoria.SUSPENSION, "Tein", 1500.00);
        coiloverTein.setDescripcion("Suspensión ajustable de alta performance");
        piezas.add(coiloverTein);

        // Frenos
        Pieza kitFrenosBrembo = new Pieza("Kit Frenos Racing", "BREM-6POT", Pieza.Categoria.FRENOS, "Brembo", 3200.00);
        kitFrenosBrembo.setDescripcion("Kit completo 6 pistones delanteros");
        piezas.add(kitFrenosBrembo);

        // Motor
        Pieza pistonesCPForged = new Pieza("Pistones Forjados", "CP-9:1", Pieza.Categoria.MOTOR, "CP Pistons", 1200.00);
        pistonesCPForged.setDescripcion("Pistones forjados relación 9:1 para turbo");
        piezas.add(pistonesCPForged);

        // Estética
        Pieza spoilerNismo = new Pieza("Alerón GT-Wing", "NISMO-GT", Pieza.Categoria.ESTETICA, "Nismo", 950.00);
        spoilerNismo.setDescripcion("Alerón GT Wing de fibra de carbono");
        piezas.add(spoilerNismo);

        logger.info("Catálogo piezas JDM cargado: {} items", piezas.size());
        guardarDatos();
    }

    public void guardarDatos() {
        try (Writer writer = new FileWriter(ARCHIVO_DATOS)) {
            gson.toJson(piezas, writer);
            logger.info("Piezas guardadas: {} registros", piezas.size());
        } catch (IOException e) {
            logger.error("Error al guardar piezas: {}", e.getMessage(), e);
        }
    }

    private void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (archivo.exists()) {
            try (Reader reader = new FileReader(ARCHIVO_DATOS)) {
                Type tipoLista = new TypeToken<ArrayList<Pieza>>() {
                }.getType();
                List<Pieza> piezasLeidas = gson.fromJson(reader, tipoLista);
                if (piezasLeidas != null) {
                    piezas = piezasLeidas;
                    logger.info("Piezas cargadas: {}", piezas.size());
                }
            } catch (IOException e) {
                logger.error("Error al cargar piezas: {}", e.getMessage(), e);
            }
        }
    }
}
