package com.autorent.domain.exception;

import com.autorent.domain.model.EstadoAuto;

/** Regla 1: solo se alquilan autos DISPONIBLE. Se lanza si está ALQUILADO o en MANTENIMIENTO (HTTP 409). */
public class AutoNoDisponibleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AutoNoDisponibleException(Long id, EstadoAuto estado) {
        super("El auto " + id + " no está disponible (estado actual: " + estado + ")");
    }
}
