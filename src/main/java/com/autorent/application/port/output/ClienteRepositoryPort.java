package com.autorent.application.port.output;

import com.autorent.application.service.repository.ClienteRepositoryService;
import com.autorent.domain.exception.ClienteNoEncontradoException;
import com.autorent.domain.model.Cliente;
import java.util.List;

/**
 * PUERTO DE SALIDA de clientes (clase concreta, SIN interfaz, con lógica).
 * Lógica: si el cliente no existe lanza ClienteNoEncontradoException.
 * Llega a la base de datos por la interfaz ClienteRepositoryService.
 */
public class ClienteRepositoryPort {

    private final ClienteRepositoryService clienteRepository;

    public ClienteRepositoryPort(ClienteRepositoryService clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente guardar(Cliente cliente) {
        return clienteRepository.guardar(cliente);
    }

    /** Devuelve el cliente o lanza ClienteNoEncontradoException (404). */
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.buscarPorId(id)
                .orElseThrow(() -> new ClienteNoEncontradoException(id));
    }

    public List<Cliente> buscarTodos() {
        return clienteRepository.buscarTodos();
    }

    public boolean existePorDocumento(String documento) {
        return clienteRepository.existePorDocumento(documento);
    }
}
