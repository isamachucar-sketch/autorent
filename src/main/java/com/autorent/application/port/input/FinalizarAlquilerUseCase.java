package com.autorent.application.port.input;

import com.autorent.application.port.output.AlquilerRepositoryPort;
import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.application.service.FinalizarAlquilerService;
import com.autorent.domain.model.Alquiler;
import com.autorent.domain.model.Auto;

/**
 * CASO DE USO (clase concreta, lógica): finalizar un alquiler. Implementa FinalizarAlquilerService.
 *  - Regla 4: no se puede finalizar dos veces (Alquiler.finalizar()).
 *  - Regla 3: el auto vuelve a DISPONIBLE (Auto.liberar()).
 */
public class FinalizarAlquilerUseCase implements FinalizarAlquilerService {

    private final AlquilerRepositoryPort alquilerRepository;
    private final AutoRepositoryPort autoRepository;

    public FinalizarAlquilerUseCase(AlquilerRepositoryPort alquilerRepository,
                                    AutoRepositoryPort autoRepository) {
        this.alquilerRepository = alquilerRepository;
        this.autoRepository = autoRepository;
    }

    @Override
    public Alquiler finalizar(Long alquilerId) {
        Alquiler alquiler = alquilerRepository.obtenerPorId(alquilerId);
        Auto auto = autoRepository.obtenerPorId(alquiler.getAutoId());

        alquiler.finalizar();   // regla 4
        auto.liberar();         // regla 3

        autoRepository.guardar(auto);
        return alquilerRepository.guardar(alquiler);
    }
}
