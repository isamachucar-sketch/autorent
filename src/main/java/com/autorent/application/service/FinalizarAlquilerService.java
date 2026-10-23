package com.autorent.application.service;

import com.autorent.domain.model.Alquiler;
import org.springframework.transaction.annotation.Transactional;

/**
 * INTERFAZ DE ENTRADA (contrato). El Controller depende de esta interfaz y la
 * implementa FinalizarAlquilerUseCase (application/port/input), que es donde está la lógica.
 */
@Transactional
public interface FinalizarAlquilerService {

    Alquiler finalizar(Long alquilerId);
}
