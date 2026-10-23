package com.autorent.application.service;

import com.autorent.domain.model.Auto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * INTERFAZ DE ENTRADA (contrato). El Controller depende de esta interfaz y la
 * implementa ConsultarAutosDisponiblesUseCase (application/port/input), que es donde está la lógica.
 */
@Transactional(readOnly = true)
public interface ConsultarAutosDisponiblesService {

    List<Auto> consultarDisponibles();
}
