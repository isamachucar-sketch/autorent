package com.autorent.application.service.repository;

import com.autorent.domain.model.Alquiler;
import java.util.List;
import java.util.Optional;

/**
 * INTERFAZ DE SALIDA (contrato hacia la base de datos).
 * La usa AlquilerRepositoryPort (application/port/output) y la implementa
 * AlquilerPersistenceAdapter (infraestructura, con Spring Data JPA).
 * Gracias a esta interfaz la aplicación no conoce JPA ni H2.
 */
public interface AlquilerRepositoryService {

    Alquiler guardar(Alquiler alquiler);

    Optional<Alquiler> buscarPorId(Long id);

    List<Alquiler> buscarTodos();

    List<Alquiler> buscarPorClienteId(Long clienteId);
}
