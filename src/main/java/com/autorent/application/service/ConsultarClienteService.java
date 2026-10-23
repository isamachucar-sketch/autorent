package com.autorent.application.service;

import com.autorent.domain.model.Cliente;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * INTERFAZ DE ENTRADA (contrato). El Controller depende de esta interfaz y la
 * implementa ConsultarClienteUseCase (application/port/input), que es donde está la lógica.
 */
@Transactional(readOnly = true)
public interface ConsultarClienteService {

    Cliente consultarPorId(Long id);

    /** EXTRA: lista todos los clientes registrados. */
    List<Cliente> consultarTodos();
}
