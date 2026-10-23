package com.autorent.infrastructure.adapter.output.persistence.repository;

import com.autorent.infrastructure.adapter.output.persistence.entity.AlquilerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlquilerJpaRepository extends JpaRepository<AlquilerEntity, Long> {

    List<AlquilerEntity> findByClienteIdOrderByIdDesc(Long clienteId);
}
