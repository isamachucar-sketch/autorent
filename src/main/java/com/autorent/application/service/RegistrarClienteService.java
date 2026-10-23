package com.autorent.application.service;

import com.autorent.domain.model.Cliente;
import org.springframework.transaction.annotation.Transactional;

/**
 * INTERFAZ DE ENTRADA (contrato). El Controller depende de esta interfaz y la
 * implementa RegistrarClienteUseCase (application/port/input), que es donde está la lógica.
 */
@Transactional
public interface RegistrarClienteService {

    /** Registra el cliente; el documento no puede repetirse. */
    Cliente registrar(Cliente cliente);
}
