package com.autorent.application.port.input;

import com.autorent.application.port.output.AlquilerRepositoryPort;
import com.autorent.application.port.output.ClienteRepositoryPort;
import com.autorent.application.service.ConsultarHistorialClienteService;
import com.autorent.domain.model.Alquiler;
import java.util.List;

/** CASO DE USO (PLUS, clase concreta): historial de alquileres de un cliente. */
public class ConsultarHistorialClienteUseCase implements ConsultarHistorialClienteService {

    private final ClienteRepositoryPort clienteRepository;
    private final AlquilerRepositoryPort alquilerRepository;

    public ConsultarHistorialClienteUseCase(ClienteRepositoryPort clienteRepository,
                                            AlquilerRepositoryPort alquilerRepository) {
        this.clienteRepository = clienteRepository;
        this.alquilerRepository = alquilerRepository;
    }

    @Override
    public List<Alquiler> consultarHistorial(Long clienteId) {
        clienteRepository.obtenerPorId(clienteId);   // 404 si el cliente no existe
        return alquilerRepository.buscarPorClienteId(clienteId);
    }
}
