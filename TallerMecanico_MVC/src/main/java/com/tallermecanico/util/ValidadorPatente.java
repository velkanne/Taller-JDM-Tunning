package com.tallermecanico.util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Validador de patentes vehiculares chilenas.
 * Soporta formatos antiguo (AA-BB-CC) y nuevo (LLLL-NN).
 */
public class ValidadorPatente {

    private static final String PATRON_ANTIGUO = "^[A-Z]{2}-[A-Z]{2}-[A-Z]{2}$";
    private static final String PATRON_NUEVO = "^[A-Z]{4}-[0-9]{2}$";

    private static final Pattern REGEX_ANTIGUO = Pattern.compile(PATRON_ANTIGUO);
    private static final Pattern REGEX_NUEVO = Pattern.compile(PATRON_NUEVO);

    /**
     * Valida si una patente cumple con el formato chileno.
     * 
     * @param patente Patente a validar (debe estar en mayúsculas)
     * @return true si la patente es válida, false en caso contrario
     */
    public static boolean validar(String patente) {
        if (patente == null || patente.trim().isEmpty()) {
            return false;
        }

        String patenteNormalizada = patente.trim().toUpperCase();

        Matcher matcherAntiguo = REGEX_ANTIGUO.matcher(patenteNormalizada);
        Matcher matcherNuevo = REGEX_NUEVO.matcher(patenteNormalizada);

        return matcherAntiguo.matches() || matcherNuevo.matches();
    }

    /**
     * Obtiene mensaje de error descriptivo para formato de patente.
     * 
     * @return Mensaje con formatos válidos
     */
    public static String getMensajeFormato() {
        return "La patente debe cumplir uno de estos formatos:\n" +
                "  • Antiguo: AA-BB-CC (6 letras mayúsculas con guiones)\n" +
                "  • Nuevo: LLLL-NN (4 letras mayúsculas + 2 dígitos)";
    }

    /**
     * Normaliza una patente a mayúsculas y sin espacios.
     * 
     * @param patente Patente a normalizar
     * @return Patente normalizada
     */
    public static String normalizar(String patente) {
        if (patente == null) {
            return "";
        }
        return patente.trim().toUpperCase();
    }
}
