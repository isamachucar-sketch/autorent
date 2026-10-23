package com.autorent.domain.exception;

/** Validación: el documento del cliente no puede repetirse (HTTP 409). */
public class DocumentoRepetidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DocumentoRepetidoException(String documento) {
        super("Ya existe un cliente con el documento " + documento);
    }
}
