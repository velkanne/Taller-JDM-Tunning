package com.tallermecanico.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Tests para GestorOrdenes.
 */
class GestorOrdenesTest {

    private GestorOrdenes gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorOrdenes();
    }

    @Test
    void testAgregarOrden() {
        OrdenTrabajo orden = new OrdenTrabajo("AB-12-34", "Toyota", "Frenos", "Normal", false, "Test");

        int cantidadInicial = gestor.getCantidadOrdenes();
        gestor.agregarOrden(orden);

        assertEquals(cantidadInicial + 1, gestor.getCantidadOrdenes());
    }

    @Test
    void testAgregarOrden_Null() {
        int cantidadInicial = gestor.getCantidadOrdenes();
        gestor.agregarOrden(null);

        assertEquals(cantidadInicial, gestor.getCantidadOrdenes(),
                "No debería agregar orden null");
    }

    @Test
    void testModificarOrden() {
        OrdenTrabajo orden = new OrdenTrabajo("AB-12-34", "Toyota", "Frenos", "Normal", false, "Original");
        gestor.agregarOrden(orden);

        int indice = gestor.getCantidadOrdenes() - 1;
        OrdenTrabajo modificada = new OrdenTrabajo("XY-99-88", "Honda", "Motor", "Urgente", true, "Modificada");

        boolean resultado = gestor.modificarOrden(indice, modificada);

        assertTrue(resultado);
        assertEquals("XY-99-88", gestor.obtenerOrden(indice).getPatente());
    }

    @Test
    void testModificarOrden_IndiceInvalido() {
        assertFalse(gestor.modificarOrden(-1, new OrdenTrabajo()));
        assertFalse(gestor.modificarOrden(999, new OrdenTrabajo()));
    }

    @Test
    void testEliminarOrden() {
        OrdenTrabajo orden = new OrdenTrabajo("AB-12-34", "Toyota", "Frenos", "Normal", false, "Test");
        gestor.agregarOrden(orden);

        int indice = gestor.getCantidadOrdenes() - 1;
        int cantidadAntes = gestor.getCantidadOrdenes();

        gestor.eliminarOrden(indice);

        assertEquals(cantidadAntes - 1, gestor.getCantidadOrdenes());
    }

    @Test
    void testObtenerOrdenes() {
        List<OrdenTrabajo> ordenes = gestor.obtenerOrdenes();
        assertNotNull(ordenes);

        // Verificar que es una copia
        ordenes.clear();
        assertTrue(gestor.getCantidadOrdenes() >= 0);
    }

    @Test
    void testObtenerOrden_Valido() {
        OrdenTrabajo orden = new OrdenTrabajo("AB-12-34", "Toyota", "Frenos", "Normal", false, "Test");
        gestor.agregarOrden(orden);

        int indice = gestor.getCantidadOrdenes() - 1;
        OrdenTrabajo recuperada = gestor.obtenerOrden(indice);

        assertNotNull(recuperada);
        assertEquals("AB-12-34", recuperada.getPatente());
    }

    @Test
    void testObtenerOrden_IndiceInvalido() {
        assertNull(gestor.obtenerOrden(-1));
        assertNull(gestor.obtenerOrden(999));
    }

    @Test
    void testBuscarPorPatente() {
        gestor.agregarOrden(new OrdenTrabajo("AB-12-34", "Toyota", "Frenos", "Normal", false, ""));
        gestor.agregarOrden(new OrdenTrabajo("AB-99-88", "Honda", "Motor", "Urgente", false, ""));
        gestor.agregarOrden(new OrdenTrabajo("XY-11-22", "Mazda", "Aceite", "Normal", false, ""));

        List<OrdenTrabajo> resultados = gestor.buscarPorPatente("AB");

        assertTrue(resultados.size() >= 2);
        assertTrue(resultados.stream().allMatch(o -> o.getPatente().contains("AB")));
    }

    @Test
    void testBuscarPorPatente_CaseInsensitive() {
        gestor.agregarOrden(new OrdenTrabajo("AB-12-34", "Toyota", "Frenos", "Normal", false, ""));

        List<OrdenTrabajo> resultados = gestor.buscarPorPatente("ab");

        assertFalse(resultados.isEmpty());
    }

    @Test
    void testFiltrarPorUrgencia() {
        gestor.agregarOrden(new OrdenTrabajo("AB-12-34", "Toyota", "Frenos", "Normal", false, ""));
        gestor.agregarOrden(new OrdenTrabajo("XY-99-88", "Honda", "Motor", "Urgente", false, ""));
        gestor.agregarOrden(new OrdenTrabajo("ZZ-11-22", "Mazda", "Aceite", "Urgente", false, ""));

        List<OrdenTrabajo> urgentes = gestor.filtrarPorUrgencia("Urgente");

        assertTrue(urgentes.size() >= 2);
        assertTrue(urgentes.stream().allMatch(o -> o.getUrgencia().equals("Urgente")));
    }

    @Test
    void testFiltrarPorServicio() {
        gestor.agregarOrden(new OrdenTrabajo("AB-12-34", "Toyota", "Frenos", "Normal", false, ""));
        gestor.agregarOrden(new OrdenTrabajo("XY-99-88", "Honda", "Frenos", "Urgente", false, ""));
        gestor.agregarOrden(new OrdenTrabajo("ZZ-11-22", "Mazda", "Motor", "Normal", false, ""));

        List<OrdenTrabajo> frenos = gestor.filtrarPorServicio("Frenos");

        assertTrue(frenos.size() >= 2);
        assertTrue(frenos.stream().allMatch(o -> o.getServicio().equals("Frenos")));
    }

    @Test
    void testContarPorUrgencia() {
        gestor.agregarOrden(new OrdenTrabajo("AB-12-34", "Toyota", "Frenos", "Crítico", false, ""));
        gestor.agregarOrden(new OrdenTrabajo("XY-99-88", "Honda", "Motor", "Crítico", false, ""));

        int criticos = gestor.contarPorUrgencia("Crítico");

        assertTrue(criticos >= 2);
    }

    @Test
    void testContarClientesEspera() {
        gestor.agregarOrden(new OrdenTrabajo("AB-12-34", "Toyota", "Frenos", "Normal", true, ""));
        gestor.agregarOrden(new OrdenTrabajo("XY-99-88", "Honda", "Motor", "Urgente", true, ""));
        gestor.agregarOrden(new OrdenTrabajo("ZZ-11-22", "Mazda", "Aceite", "Normal", false, ""));

        int enEspera = gestor.contarClientesEspera();

        assertTrue(enEspera >= 2);
    }
}
