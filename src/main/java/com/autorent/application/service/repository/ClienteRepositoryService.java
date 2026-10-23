package com.autorent.application.service.repository;

import com.autorent.domain.model.Cliente;
import java.util.List;
import java.util.Optional;

/**
 * INTERFAZ DE SALIDA (contrato hacia la base de datos).
 * La usa ClienteRepositoryPort (application/port/output) y la implementa
 * ClientePersistenceAdapter (infraestructura, con Spring Data JPA).
 * Gracias a esta interfaz la aplicación no conoce JPA ni H2.
 */
public interface ClienteRepositoryService {

    Cliente guardar(Cliente cliente);

    Optional<Cliente> buscarPorId(Long id);

    List<Cliente> buscarTodos();

    boolean existePorDocumento(String documento);
}
