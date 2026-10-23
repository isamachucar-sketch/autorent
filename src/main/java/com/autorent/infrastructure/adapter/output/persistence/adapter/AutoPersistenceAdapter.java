package com.autorent.infrastructure.adapter.output.persistence.adapter;

import com.autorent.application.service.repository.AutoRepositoryService;
import com.autorent.domain.model.Auto;
import com.autorent.domain.model.EstadoAuto;
import com.autorent.infrastructure.adapter.output.persistence.entity.AutoEntity;
import com.autorent.infrastructure.adapter.output.persistence.repository.AutoJpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * ADAPTADOR DE SALIDA: implementa la interfaz AutoRepositoryService usando Spring Data JPA.
 * Es el único que conoce AutoJpaRepository y el único que convierte Auto <-> AutoEntity.
 */
@Component
public class AutoPersistenceAdapter implements AutoRepositoryService {

    private final AutoJpaRepository jpaRepository;

    public AutoPersistenceAdapter(AutoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Auto guardar(Auto auto) {
        return aDominio(jpaRepository.save(aEntidad(auto)));
    }

    @Override
    public Optional<Auto> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(this::aDominio);
    }

    @Override
    public List<Auto> buscarDisponibles() {
        return jpaRepository.findByEstado(EstadoAuto.DISPONIBLE).stream().map(this::aDominio).toList();
    }

    @Override
    public boolean existePorPlaca(String placa) {
        return jpaRepository.existsByPlaca(placa);
    }

    private AutoEntity aEntidad(Auto auto) {
        AutoEntity entity = new AutoEntity();
        entity.setId(auto.getId());
        entity.setPlaca(auto.getPlaca());
        entity.setMarca(auto.getMarca());
        entity.setModelo(auto.getModelo());
        entity.setAnio(auto.getAnio());
        entity.setPrecioPorDia(auto.getPrecioPorDia());
        entity.setDeposito(auto.getDeposito());
        entity.setEstado(auto.getEstado());
        return entity;
    }

    private Auto aDominio(AutoEntity entity) {
        Auto auto = new Auto();
        auto.setId(entity.getId());
        auto.setPlaca(entity.getPlaca());
        auto.setMarca(entity.getMarca());
        auto.setModelo(entity.getModelo());
        auto.setAnio(entity.getAnio());
        auto.setPrecioPorDia(entity.getPrecioPorDia());
        auto.setDeposito(entity.getDeposito());
        auto.setEstado(entity.getEstado());
        return auto;
    }
}
