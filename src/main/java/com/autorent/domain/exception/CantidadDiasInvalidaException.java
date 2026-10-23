package com.autorent.domain.exception;

/** Regla 5: la cantidad de días debe ser mayor que cero (HTTP 400). */
public class CantidadDiasInvalidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CantidadDiasInvalidaException(int cantidadDias) {
        super("La cantidad de días debe ser mayor que cero (recibido: " + cantidadDias + ")");
    }
}
