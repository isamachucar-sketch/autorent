package com.autorent.infrastructure.adapter.output.persistence.repository;

import com.autorent.domain.model.EstadoAuto;
import com.autorent.infrastructure.adapter.output.persistence.entity.AutoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** Solo lo conoce el adaptador de persistencia. */
public interface AutoJpaRepository extends JpaRepository<AutoEntity, Long> {

    List<AutoEntity> findByEstado(EstadoAuto estado);

    boolean existsByPlaca(String placa);
}
