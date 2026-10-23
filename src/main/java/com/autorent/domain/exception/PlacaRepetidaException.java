package com.autorent.domain.exception;

/** Validación: la placa del auto no puede repetirse (HTTP 409). */
public class PlacaRepetidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PlacaRepetidaException(String placa) {
        super("Ya existe un auto con la placa " + placa);
    }
}
