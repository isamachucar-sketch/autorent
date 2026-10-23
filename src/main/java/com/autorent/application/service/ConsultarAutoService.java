package com.autorent.application.service;

import com.autorent.domain.model.Auto;
import org.springframework.transaction.annotation.Transactional;

/**
 * INTERFAZ DE ENTRADA (contrato). El Controller depende de esta interfaz y la
 * implementa ConsultarAutoUseCase (application/port/input), que es donde está la lógica.
 */
@Transactional(readOnly = true)
public interface ConsultarAutoService {

    Auto consultarPorId(Long id);
}
