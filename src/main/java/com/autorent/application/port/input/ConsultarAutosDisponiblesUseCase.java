package com.autorent.application.port.input;

import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.application.service.ConsultarAutosDisponiblesService;
import com.autorent.domain.model.Auto;
import java.util.List;

/** CASO DE USO (clase concreta): listar solo los autos DISPONIBLE. Implementa ConsultarAutosDisponiblesService. */
public class ConsultarAutosDisponiblesUseCase implements ConsultarAutosDisponiblesService {

    private final AutoRepositoryPort autoRepository;

    public ConsultarAutosDisponiblesUseCase(AutoRepositoryPort autoRepository) {
        this.autoRepository = autoRepository;
    }

    @Override
    public List<Auto> consultarDisponibles() {
        return autoRepository.buscarDisponibles();
    }
}
