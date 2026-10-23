package com.autorent.application.port.output;

import com.autorent.application.service.repository.AlquilerRepositoryService;
import com.autorent.domain.exception.AlquilerNoEncontradoException;
import com.autorent.domain.model.Alquiler;
import java.util.List;

/**
 * PUERTO DE SALIDA de alquileres (clase concreta, SIN interfaz, con lógica).
 * Lógica: si el alquiler no existe lanza AlquilerNoEncontradoException.
 * Llega a la base de datos por la interfaz AlquilerRepositoryService.
 */
public class AlquilerRepositoryPort {

    private final AlquilerRepositoryService alquilerRepository;

    public AlquilerRepositoryPort(AlquilerRepositoryService alquilerRepository) {
        this.alquilerRepository = alquilerRepository;
    }

    public Alquiler guardar(Alquiler alquiler) {
        return alquilerRepository.guardar(alquiler);
    }

    /** Devuelve el alquiler o lanza AlquilerNoEncontradoException (404). */
    public Alquiler obtenerPorId(Long id) {
        return alquilerRepository.buscarPorId(id)
                .orElseThrow(() -> new AlquilerNoEncontradoException(id));
    }

    public List<Alquiler> buscarTodos() {
        return alquilerRepository.buscarTodos();
    }

    /** PLUS: alquileres del cliente, del más reciente al más antiguo. */
    public List<Alquiler> buscarPorClienteId(Long clienteId) {
        return alquilerRepository.buscarPorClienteId(clienteId);
    }
}
