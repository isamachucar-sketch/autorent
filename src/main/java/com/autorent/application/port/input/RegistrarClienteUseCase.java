package com.autorent.application.port.input;

import com.autorent.application.port.output.ClienteRepositoryPort;
import com.autorent.application.service.RegistrarClienteService;
import com.autorent.domain.exception.DocumentoRepetidoException;
import com.autorent.domain.model.Cliente;

/** CASO DE USO (clase concreta): registrar un cliente. Lógica: el documento no puede repetirse. */
public class RegistrarClienteUseCase implements RegistrarClienteService {

    private final ClienteRepositoryPort clienteRepository;

    public RegistrarClienteUseCase(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente registrar(Cliente cliente) {
        if (clienteRepository.existePorDocumento(cliente.getDocumento())) {
            throw new DocumentoRepetidoException(cliente.getDocumento());
        }
        cliente.setId(null);
        return clienteRepository.guardar(cliente);
    }
}
