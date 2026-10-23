package com.autorent.domain.exception;

/** Se lanza cuando no existe un auto con el id indicado (HTTP 404). */
public class AutoNoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AutoNoEncontradoException(Long id) {
        super("No existe un auto con id " + id);
    }
}
