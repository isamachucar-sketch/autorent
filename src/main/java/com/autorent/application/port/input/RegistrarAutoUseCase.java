package com.autorent.application.port.input;

import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.application.service.RegistrarAutoService;
import com.autorent.domain.exception.PlacaRepetidaException;
import com.autorent.domain.model.Auto;
import com.autorent.domain.model.EstadoAuto;

/**
 * CASO DE USO (clase concreta, lógica): registrar un auto.
 * Implementa la interfaz RegistrarAutoService, que es la que usa el Controller.
 * Lógica: la placa no se repite y el auto nace DISPONIBLE.
 */
public class RegistrarAutoUseCase implements RegistrarAutoService {

    private final AutoRepositoryPort autoRepository;

    public RegistrarAutoUseCase(AutoRepositoryPort autoRepository) {
        this.autoRepository = autoRepository;
    }

    @Override
    public Auto registrar(Auto auto) {
        if (autoRepository.existePorPlaca(auto.getPlaca())) {
            throw new PlacaRepetidaException(auto.getPlaca());
        }
        auto.setId(null);
        auto.setEstado(EstadoAuto.DISPONIBLE);
        return autoRepository.guardar(auto);
    }
}
