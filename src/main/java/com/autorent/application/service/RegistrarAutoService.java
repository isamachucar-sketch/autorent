package com.autorent.application.service;

import com.autorent.domain.model.Auto;
import org.springframework.transaction.annotation.Transactional;

/**
 * INTERFAZ DE ENTRADA (contrato). El Controller depende de esta interfaz y la
 * implementa RegistrarAutoUseCase (application/port/input), que es donde está la lógica.
 */
@Transactional
public interface RegistrarAutoService {

    /** Registra el auto con estado inicial DISPONIBLE. */
    Auto registrar(Auto auto);
}
