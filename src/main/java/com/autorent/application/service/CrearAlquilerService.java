package com.autorent.application.service;

import com.autorent.domain.model.Alquiler;
import org.springframework.transaction.annotation.Transactional;

/**
 * INTERFAZ DE ENTRADA (contrato). El Controller depende de esta interfaz y la
 * implementa CrearAlquilerUseCase (application/port/input), que es donde está la lógica.
 */
@Transactional
public interface CrearAlquilerService {

    /** El valor total NO se recibe: lo calcula el negocio. */
    Alquiler crear(Long clienteId, Long autoId, int cantidadDias);
}
