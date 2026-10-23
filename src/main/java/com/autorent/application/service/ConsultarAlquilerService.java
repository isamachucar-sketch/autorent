package com.autorent.application.service;

import com.autorent.domain.model.Alquiler;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * INTERFAZ DE ENTRADA (contrato). El Controller depende de esta interfaz y la
 * implementa ConsultarAlquilerUseCase (application/port/input), que es donde está la lógica.
 */
@Transactional(readOnly = true)
public interface ConsultarAlquilerService {

    Alquiler consultarPorId(Long id);

    /** EXTRA: lista todos los alquileres. */
    List<Alquiler> consultarTodos();
}
