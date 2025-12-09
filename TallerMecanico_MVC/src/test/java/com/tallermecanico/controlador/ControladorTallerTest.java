package com.tallermecanico.controlador;

import com.tallermecanico.modelo.OrdenTrabajo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

/**
 * Tests para ControladorTaller.
 */
class ControladorTallerTest {

    private ControladorTaller controlador;

    @BeforeEach
    void setUp() {
        controlador = new ControladorTaller();
    }

    @Test
    void testAgregarOrden_Valido() {
        boolean resultado = controlador.agregarOrden(
                "AB-12-34",
                "Toyota Corolla",
                "Frenos",
                "Normal",
                false,
                "Test");

        assertTrue(resultado);
    }

    @Test
    void testAgregarOrden_PatenteVacia() {
        boolean resultado = controlador.agregarOrden(
                "",
                "Toyota",
                "Frenos",
                "Normal",
                false,
                "Test");

        assertFalse(resultado, "No debería permitir patente vacía");
    }

    @Test
    void testAgregarOrden_ModeloVacio() {
        boolean resultado = controlador.agregarOrden(
                "AB-12-34",
                "",
                "Frenos",
                "Normal",
                false,
                "Test");

        assertFalse(resultado, "No debería permitir modelo vacío");
    }

    @Test
    void testAgregarOrden_PatenteInvalida() {
        boolean resultado = controlador.agregarOrden(
                "INVALIDA",
                "Toyota",
                "Frenos",
                "Normal",
                false,
                "Test");

        assertFalse(resultado, "No debería permitir formato patente inválido");
    }

    @Test
    void testModificarOrden_Valido() {
        controlador.agregarOrden("AB-12-34", "Toyota", "Frenos", "Normal", false, "Original");

        int indice = controlador.getCantidadOrdenes() - 1;
        boolean resultado = controlador.modificarOrden(
                indice,
                "XY-99-88",
                "Honda",
                "Motor",
                "Urgente",
                true,
                "Modificada");

        assertTrue(resultado);
    }

    @Test
    void testModificarOrden_ValidacionFalla() {
        controlador.agregarOrden("AB-12-34", "Toyota", "Frenos", "Normal", false, "Original");

        int indice = controlador.getCantidadOrdenes() - 1;
        boolean resultado = controlador.modificarOrden(
                indice,
                "",
                "Honda",
                "Motor",
                "Urgente",
                true,
                "Modificada");

        assertFalse(resultado, "No debería modificar con patente vacía");
    }

    @Test
    void testEliminarOrden() {
        controlador.agregarOrden("AB-12-34", "Toyota", "Frenos", "Normal", false, "Test");

        int cantidadAntes = controlador.getCantidadOrdenes();
        int indice = cantidadAntes - 1;

        controlador.eliminarOrden(indice);

        assertEquals(cantidadAntes - 1, controlador.getCantidadOrdenes());
    }

    @Test
    void testObtenerTodasLasOrdenes() {
        List<OrdenTrabajo> ordenes = controlador.obtenerTodasLasOrdenes();
        assertNotNull(ordenes);
    }

    @Test
    void testBuscarPorPatente() {
        controlador.agregarOrden("AB-12-34", "Toyota", "Frenos", "Normal", false, "");

        List<OrdenTrabajo> resultados = controlador.buscarPorPatente("AB");

        assertFalse(resultados.isEmpty());
    }

    @Test
    void testFiltrarPorUrgencia() {
        controlador.agregarOrden("AB-12-34", "Toyota", "Frenos", "Urgente", false, "");

        List<OrdenTrabajo> urgentes = controlador.filtrarPorUrgencia("Urgente");

        assertFalse(urgentes.isEmpty());
    }

    @Test
    void testFiltrarPorServicio() {
        controlador.agregarOrden("AB-12-34", "Toyota", "Motor", "Normal", false, "");

        List<OrdenTrabajo> motor = controlador.filtrarPorServicio("Motor");

        assertFalse(motor.isEmpty());
    }

    @Test
    void testObtenerEstadisticas() {
        controlador.agregarOrden("AB-12-34", "Toyota", "Frenos", "Normal", false, "");
        controlador.agregarOrden("XY-99-88", "Honda", "Motor", "Urgente", true, "");
        controlador.agregarOrden("ZZ-11-22", "Mazda", "Aceite", "Crítico", true, "");

        Map<String, Integer> stats = controlador.obtenerEstadisticas();

        assertNotNull(stats);
        assertTrue(stats.containsKey("total"));
        assertTrue(stats.containsKey("normal"));
        assertTrue(stats.containsKey("urgente"));
        assertTrue(stats.containsKey("critico"));
        assertTrue(stats.containsKey("espera"));

        assertTrue(stats.get("total") >= 3);
        assertTrue(stats.get("espera") >= 2);
    }

    @Test
    void testGetCantidadOrdenes() {
        int cantidadInicial = controlador.getCantidadOrdenes();

        controlador.agregarOrden("AB-12-34", "Toyota", "Frenos", "Normal", false, "");

        assertEquals(cantidadInicial + 1, controlador.getCantidadOrdenes());
    }
}
