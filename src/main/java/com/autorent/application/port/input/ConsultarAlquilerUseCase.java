package com.autorent.application.port.input;

import com.autorent.application.port.output.AlquilerRepositoryPort;
import com.autorent.application.service.ConsultarAlquilerService;
import com.autorent.domain.model.Alquiler;
import java.util.List;

/** CASO DE USO (clase concreta): consultar alquileres (por id o todos). Implementa ConsultarAlquilerService. */
public class ConsultarAlquilerUseCase implements ConsultarAlquilerService {

    private final AlquilerRepositoryPort alquilerRepository;

    public ConsultarAlquilerUseCase(AlquilerRepositoryPort alquilerRepository) {
        this.alquilerRepository = alquilerRepository;
    }

    @Override
    public Alquiler consultarPorId(Long id) {
        return alquilerRepository.obtenerPorId(id);   // 404 si no existe
    }

    @Override
    public List<Alquiler> consultarTodos() {
        return alquilerRepository.buscarTodos();
    }
}
