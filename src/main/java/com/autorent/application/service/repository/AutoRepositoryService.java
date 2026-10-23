package com.autorent.application.service.repository;

import com.autorent.domain.model.Auto;
import java.util.List;
import java.util.Optional;

/**
 * INTERFAZ DE SALIDA (contrato hacia la base de datos).
 * La usa AutoRepositoryPort (application/port/output) y la implementa
 * AutoPersistenceAdapter (infraestructura, con Spring Data JPA).
 * Gracias a esta interfaz la aplicación no conoce JPA ni H2.
 */
public interface AutoRepositoryService {

    Auto guardar(Auto auto);

    Optional<Auto> buscarPorId(Long id);

    List<Auto> buscarDisponibles();

    boolean existePorPlaca(String placa);
}
