package com.autorent.application.port.output;

import com.autorent.application.service.repository.AutoRepositoryService;
import com.autorent.domain.exception.AutoNoEncontradoException;
import com.autorent.domain.model.Auto;
import java.util.List;

/**
 * PUERTO DE SALIDA de autos (clase concreta, SIN interfaz, con lógica).
 * Lógica: si el auto no existe lanza AutoNoEncontradoException.
 * Para llegar a la base de datos usa la interfaz AutoRepositoryService,
 * así no conoce JPA ni H2.
 */
public class AutoRepositoryPort {

    private final AutoRepositoryService autoRepository;

    public AutoRepositoryPort(AutoRepositoryService autoRepository) {
        this.autoRepository = autoRepository;
    }

    public Auto guardar(Auto auto) {
        return autoRepository.guardar(auto);
    }

    /** Devuelve el auto o lanza AutoNoEncontradoException (404). */
    public Auto obtenerPorId(Long id) {
        return autoRepository.buscarPorId(id)
                .orElseThrow(() -> new AutoNoEncontradoException(id));
    }

    public List<Auto> buscarDisponibles() {
        return autoRepository.buscarDisponibles();
    }

    public boolean existePorPlaca(String placa) {
        return autoRepository.existePorPlaca(placa);
    }
}
