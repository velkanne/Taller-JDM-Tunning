package com.tallermecanico.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para modelo OrdenTrabajo.
 */
class OrdenTrabajoTest {

    @Test
    void testConstructorCompleto() {
        OrdenTrabajo orden = new OrdenTrabajo(
                "AB-12-34",
                "Toyota Corolla",
                "Frenos",
                "Urgente",
                true,
                "Cliente espera en sala");

        assertEquals("AB-12-34", orden.getPatente());
        assertEquals("Toyota Corolla", orden.getModeloAuto());
        assertEquals("Frenos", orden.getServicio());
        assertEquals("Urgente", orden.getUrgencia());
        assertTrue(orden.isClienteEspera());
        assertEquals("Cliente espera en sala", orden.getObservaciones());
    }

    @Test
    void testConstructorVacio() {
        OrdenTrabajo orden = new OrdenTrabajo();

        assertEquals("", orden.getPatente());
        assertEquals("", orden.getModeloAuto());
        assertEquals("Mantención", orden.getServicio());
        assertEquals("Normal", orden.getUrgencia());
        assertFalse(orden.isClienteEspera());
        assertEquals("", orden.getObservaciones());
    }

    @Test
    void testSettersGetters() {
        OrdenTrabajo orden = new OrdenTrabajo();

        orden.setPatente("XY-99-88");
        orden.setModeloAuto("Honda Civic");
        orden.setServicio("Aceite");
        orden.setUrgencia("Crítico");
        orden.setClienteEspera(true);
        orden.setObservaciones("Urgente revisar");

        assertEquals("XY-99-88", orden.getPatente());
        assertEquals("Honda Civic", orden.getModeloAuto());
        assertEquals("Aceite", orden.getServicio());
        assertEquals("Crítico", orden.getUrgencia());
        assertTrue(orden.isClienteEspera());
        assertEquals("Urgente revisar", orden.getObservaciones());
    }

    @Test
    void testCopiar_DeepCopy() {
        OrdenTrabajo original = new OrdenTrabajo(
                "AB-12-34",
                "Toyota",
                "Motor",
                "Normal",
                false,
                "Test");

        OrdenTrabajo copia = original.copiar();

        assertNotSame(original, copia, "Copiar debería crear instancia nueva");
        assertEquals(original.getPatente(), copia.getPatente());
        assertEquals(original.getModeloAuto(), copia.getModeloAuto());
        assertEquals(original.getServicio(), copia.getServicio());
        assertEquals(original.getUrgencia(), copia.getUrgencia());
        assertEquals(original.isClienteEspera(), copia.isClienteEspera());
        assertEquals(original.getObservaciones(), copia.getObservaciones());

        // Modificar copia no debe afectar original
        copia.setPatente("XY-99-88");
        assertNotEquals(original.getPatente(), copia.getPatente());
    }

    @Test
    void testToString() {
        OrdenTrabajo orden = new OrdenTrabajo(
                "AB-12-34",
                "Toyota",
                "Frenos",
                "Urgente",
                true,
                "Observación corta");

        String str = orden.toString();

        assertTrue(str.contains("AB-12-34"));
        assertTrue(str.contains("Toyota"));
        assertTrue(str.contains("Frenos"));
        assertTrue(str.contains("Urgente"));
        assertTrue(str.contains("Sí") || str.contains("true"));
    }

    @Test
    void testToString_ObservacionLarga() {
        OrdenTrabajo orden = new OrdenTrabajo(
                "AB-12-34",
                "Toyota",
                "Motor",
                "Normal",
                false,
                "Esta es una observación muy larga que debería ser truncada en el toString");

        String str = orden.toString();

        // Verificar que observación larga se trunca
        assertTrue(str.contains("...") || str.length() < orden.getObservaciones().length());
    }
}
