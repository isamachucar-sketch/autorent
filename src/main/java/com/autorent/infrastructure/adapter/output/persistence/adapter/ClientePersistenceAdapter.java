package com.autorent.infrastructure.adapter.output.persistence.adapter;

import com.autorent.application.service.repository.ClienteRepositoryService;
import com.autorent.domain.model.Cliente;
import com.autorent.infrastructure.adapter.output.persistence.entity.ClienteEntity;
import com.autorent.infrastructure.adapter.output.persistence.repository.ClienteJpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** ADAPTADOR DE SALIDA: implementa la interfaz ClienteRepositoryService con Spring Data JPA. */
@Component
public class ClientePersistenceAdapter implements ClienteRepositoryService {

    private final ClienteJpaRepository jpaRepository;

    public ClientePersistenceAdapter(ClienteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return aDominio(jpaRepository.save(aEntidad(cliente)));
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(this::aDominio);
    }

    @Override
    public List<Cliente> buscarTodos() {
        return jpaRepository.findAll().stream().map(this::aDominio).toList();
    }

    @Override
    public boolean existePorDocumento(String documento) {
        return jpaRepository.existsByDocumento(documento);
    }

    private ClienteEntity aEntidad(Cliente cliente) {
        ClienteEntity entity = new ClienteEntity();
        entity.setId(cliente.getId());
        entity.setNombre(cliente.getNombre());
        entity.setDocumento(cliente.getDocumento());
        entity.setTelefono(cliente.getTelefono());
        entity.setEmail(cliente.getEmail());
        return entity;
    }

    private Cliente aDominio(ClienteEntity entity) {
        Cliente cliente = new Cliente();
        cliente.setId(entity.getId());
        cliente.setNombre(entity.getNombre());
        cliente.setDocumento(entity.getDocumento());
        cliente.setTelefono(entity.getTelefono());
        cliente.setEmail(entity.getEmail());
        return cliente;
    }
}
