package com.tallermecanico.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ValidadorPatente.
 */
class ValidadorPatenteTest {

    @ParameterizedTest
    @ValueSource(strings = { "AB-12-34", "XY-99-88", "AA-BB-CC" })
    void testValidarFormatoAntiguo_Valido(String patente) {
        assertTrue(ValidadorPatente.validar(patente),
                "La patente " + patente + " debería ser válida (formato antiguo)");
    }

    @ParameterizedTest
    @ValueSource(strings = { "ABCD-12", "XYZW-99", "LLLL-00" })
    void testValidarFormatoNuevo_Valido(String patente) {
        assertTrue(ValidadorPatente.validar(patente),
                "La patente " + patente + " debería ser válida (formato nuevo)");
    }

    @ParameterizedTest
    @ValueSource(strings = { "AB-12-3", "AB-1-34", "A-12-34", "AB12-34", "AB-12-345" })
    void testValidarFormatoAntiguo_Invalido(String patente) {
        assertFalse(ValidadorPatente.validar(patente),
                "La patente " + patente + " debería ser inválida");
    }

    @ParameterizedTest
    @ValueSource(strings = { "ABC-12", "ABCDE-12", "ABCD-1", "ABCD-123", "1234-12" })
    void testValidarFormatoNuevo_Invalido(String patente) {
        assertFalse(ValidadorPatente.validar(patente),
                "La patente " + patente + " debería ser inválida");
    }

    @Test
    void testValidar_Null() {
        assertFalse(ValidadorPatente.validar(null),
                "Patente null debería ser inválida");
    }

    @Test
    void testValidar_Vacio() {
        assertFalse(ValidadorPatente.validar(""),
                "Patente vacía debería ser inválida");
    }

    @Test
    void testValidar_ConEspacios() {
        assertFalse(ValidadorPatente.validar("  AB-12-34  "),
                "Patente con espacios debería ser inválida");
    }

    @Test
    void testNormalizar_Minuscula() {
        assertEquals("AB-12-34", ValidadorPatente.normalizar("ab-12-34"),
                "Normalizar debería convertir a mayúsculas");
    }

    @Test
    void testNormalizar_Mixta() {
        assertEquals("ABCD-12", ValidadorPatente.normalizar("AbCd-12"),
                "Normalizar debería convertir a mayúsculas");
    }

    @Test
    void testNormalizar_YaMayuscula() {
        assertEquals("XY-99-88", ValidadorPatente.normalizar("XY-99-88"),
                "Normalizar no debería alterar patente ya en mayúsculas");
    }

    @Test
    void testNormalizar_Null() {
        assertEquals("", ValidadorPatente.normalizar(null),
                "Normalizar null debería retornar cadena vacía");
    }

    @Test
    void testGetMensajeFormato() {
        String mensaje = ValidadorPatente.getMensajeFormato();
        assertNotNull(mensaje);
        assertTrue(mensaje.contains("AA-BB-CC") || mensaje.contains("LLLL-NN"),
                "Mensaje debería contener ejemplos de formatos");
    }
}
