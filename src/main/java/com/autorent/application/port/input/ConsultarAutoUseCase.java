package com.autorent.application.port.input;

import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.application.service.ConsultarAutoService;
import com.autorent.domain.model.Auto;

/** CASO DE USO (clase concreta): consultar un auto por id. Implementa ConsultarAutoService. */
public class ConsultarAutoUseCase implements ConsultarAutoService {

    private final AutoRepositoryPort autoRepository;

    public ConsultarAutoUseCase(AutoRepositoryPort autoRepository) {
        this.autoRepository = autoRepository;
    }

    @Override
    public Auto consultarPorId(Long id) {
        return autoRepository.obtenerPorId(id);   // 404 si no existe
    }
}
