package com.tallermecanico.exception;

/**
 * Excepción personalizada para errores del sistema de taller mecánico.
 */
public class TallerException extends Exception {

    public TallerException(String mensaje) {
        super(mensaje);
    }

    public TallerException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public TallerException(Throwable causa) {
        super(causa);
    }
}
