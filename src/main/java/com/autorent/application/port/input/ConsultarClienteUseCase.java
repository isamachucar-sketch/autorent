package com.autorent.application.port.input;

import com.autorent.application.port.output.ClienteRepositoryPort;
import com.autorent.application.service.ConsultarClienteService;
import com.autorent.domain.model.Cliente;
import java.util.List;

/** CASO DE USO (clase concreta): consultar clientes (por id o todos). Implementa ConsultarClienteService. */
public class ConsultarClienteUseCase implements ConsultarClienteService {

    private final ClienteRepositoryPort clienteRepository;

    public ConsultarClienteUseCase(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente consultarPorId(Long id) {
        return clienteRepository.obtenerPorId(id);   // 404 si no existe
    }

    @Override
    public List<Cliente> consultarTodos() {
        return clienteRepository.buscarTodos();
    }
}
