package com.tallermecanico.modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gestor de Clientes para JDM Tuning Shop.
 */
public class GestorClientes {
    private static final Logger logger = LoggerFactory.getLogger(GestorClientes.class);
    private List<Cliente> clientes;
    private static final String ARCHIVO_DATOS = "clientes.json";
    private final Gson gson;

    public GestorClientes() {
        this.clientes = new ArrayList<>();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        logger.info("Inicializando GestorClientes");
        cargarDatos();
    }

    public void agregarCliente(Cliente cliente) {
        if (cliente != null) {
            clientes.add(cliente);
            logger.debug("Cliente agregado: {}", cliente.getNombre());
        }
    }

    public boolean modificarCliente(String id, Cliente clienteActualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId().equals(id)) {
                clientes.set(i, clienteActualizado);
                return true;
            }
        }
        return false;
    }

    public void eliminarCliente(String id) {
        clientes.removeIf(c -> c.getId().equals(id));
    }

    public Cliente obtenerCliente(String id) {
        return clientes.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientes);
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>(clientes);
        }
        String busqueda = nombre.trim().toLowerCase();
        return clientes.stream()
                .filter(c -> c.getNombre().toLowerCase().contains(busqueda))
                .collect(Collectors.toList());
    }

    public List<Cliente> filtrarPorTipo(Cliente.TipoCliente tipo) {
        return clientes.stream()
                .filter(c -> c.getTipo() == tipo)
                .collect(Collectors.toList());
    }

    public int getCantidad() {
        return clientes.size();
    }

    public void guardarDatos() {
        try (Writer writer = new FileWriter(ARCHIVO_DATOS)) {
            gson.toJson(clientes, writer);
            logger.info("Datos clientes guardados: {} registros", clientes.size());
        } catch (IOException e) {
            logger.error("Error al guardar clientes: {}", e.getMessage(), e);
        }
    }

    private void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (archivo.exists()) {
            try (Reader reader = new FileReader(ARCHIVO_DATOS)) {
                Type tipoLista = new TypeToken<ArrayList<Cliente>>() {
                }.getType();
                List<Cliente> clientesLeidos = gson.fromJson(reader, tipoLista);
                if (clientesLeidos != null) {
                    clientes = clientesLeidos;
                    logger.info("Clientes cargados: {}", clientes.size());
                } else {
                    logger.warn("Archivo clientes vacío");
                }
            } catch (IOException e) {
                logger.error("Error al cargar clientes: {}", e.getMessage(), e);
            }
        } else {
            logger.info("Archivo clientes no existe, iniciando vacío");
        }
    }
}
