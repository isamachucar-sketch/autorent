package com.autorent.infrastructure.adapter.output.persistence.adapter;

import com.autorent.application.service.repository.AlquilerRepositoryService;
import com.autorent.domain.model.Alquiler;
import com.autorent.infrastructure.adapter.output.persistence.entity.AlquilerEntity;
import com.autorent.infrastructure.adapter.output.persistence.repository.AlquilerJpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** ADAPTADOR DE SALIDA: implementa la interfaz AlquilerRepositoryService con Spring Data JPA. */
@Component
public class AlquilerPersistenceAdapter implements AlquilerRepositoryService {

    private final AlquilerJpaRepository jpaRepository;

    public AlquilerPersistenceAdapter(AlquilerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Alquiler guardar(Alquiler alquiler) {
        return aDominio(jpaRepository.save(aEntidad(alquiler)));
    }

    @Override
    public Optional<Alquiler> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(this::aDominio);
    }

    @Override
    public List<Alquiler> buscarTodos() {
        return jpaRepository.findAll().stream().map(this::aDominio).toList();
    }

    @Override
    public List<Alquiler> buscarPorClienteId(Long clienteId) {
        return jpaRepository.findByClienteIdOrderByIdDesc(clienteId).stream().map(this::aDominio).toList();
    }

    private AlquilerEntity aEntidad(Alquiler alquiler) {
        AlquilerEntity entity = new AlquilerEntity();
        entity.setId(alquiler.getId());
        entity.setClienteId(alquiler.getClienteId());
        entity.setAutoId(alquiler.getAutoId());
        entity.setFechaInicio(alquiler.getFechaInicio());
        entity.setFechaFin(alquiler.getFechaFin());
        entity.setCantidadDias(alquiler.getCantidadDias());
        entity.setValorTotal(alquiler.getValorTotal());
        entity.setValorTotalConDeposito(alquiler.getValorTotalConDeposito());
        entity.setEstado(alquiler.getEstado());
        return entity;
    }

    private Alquiler aDominio(AlquilerEntity entity) {
        Alquiler alquiler = new Alquiler();
        alquiler.setId(entity.getId());
        alquiler.setClienteId(entity.getClienteId());
        alquiler.setAutoId(entity.getAutoId());
        alquiler.setFechaInicio(entity.getFechaInicio());
        alquiler.setFechaFin(entity.getFechaFin());
        alquiler.setCantidadDias(entity.getCantidadDias());
        alquiler.setValorTotal(entity.getValorTotal());
        alquiler.setValorTotalConDeposito(entity.getValorTotalConDeposito());
        alquiler.setEstado(entity.getEstado());
        return alquiler;
    }
}
