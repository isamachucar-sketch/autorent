package com.autorent.domain.exception;

/** Regla 4: un alquiler no se puede finalizar dos veces (HTTP 409). */
public class AlquilerYaFinalizadoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AlquilerYaFinalizadoException(Long id) {
        super("El alquiler " + id + " ya fue finalizado");
    }
}
