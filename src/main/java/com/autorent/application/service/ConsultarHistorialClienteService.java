package com.autorent.application.service;

import com.autorent.domain.model.Alquiler;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * INTERFAZ DE ENTRADA (contrato). El Controller depende de esta interfaz y la
 * implementa ConsultarHistorialClienteUseCase (application/port/input), que es donde está la lógica.
 */
@Transactional(readOnly = true)
public interface ConsultarHistorialClienteService {

    /** PLUS: historial de alquileres de un cliente (activos y finalizados). */
    List<Alquiler> consultarHistorial(Long clienteId);
}
