package com.autorent.domain.exception;

/** Se lanza cuando no existe un cliente con el id indicado (HTTP 404). */
public class ClienteNoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ClienteNoEncontradoException(Long id) {
        super("No existe un cliente con id " + id);
    }
}
