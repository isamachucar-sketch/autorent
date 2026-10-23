package com.autorent.domain.exception;

/** Se lanza cuando no existe un alquiler con el id indicado (HTTP 404). */
public class AlquilerNoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AlquilerNoEncontradoException(Long id) {
        super("No existe un alquiler con id " + id);
    }
}
