package com.autorent.application.port.input;

import com.autorent.application.port.output.AlquilerRepositoryPort;
import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.application.port.output.ClienteRepositoryPort;
import com.autorent.application.service.CrearAlquilerService;
import com.autorent.domain.model.Alquiler;
import com.autorent.domain.model.Auto;
import java.time.LocalDate;

/**
 * CASO DE USO (clase concreta, lógica): crear un alquiler. Implementa CrearAlquilerService.
 *  1. El cliente debe existir   -> ClienteNoEncontradoException (lo lanza ClienteRepositoryPort)
 *  2. El auto debe existir      -> AutoNoEncontradoException    (lo lanza AutoRepositoryPort)
 *  3. Alquiler.iniciar(...) aplica las reglas del dominio:
 *     días > 0 (regla 5), auto DISPONIBLE -> ALQUILADO (reglas 1 y 2),
 *     valorTotal = precioPorDia x cantidadDias (regla 6).
 *  4. Guarda el auto (ALQUILADO) y el alquiler por los puertos de salida.
 * El valorTotal NUNCA se recibe como parámetro: lo calcula el negocio.
 */
public class CrearAlquilerUseCase implements CrearAlquilerService {

    private final AlquilerRepositoryPort alquilerRepository;
    private final AutoRepositoryPort autoRepository;
    private final ClienteRepositoryPort clienteRepository;

    public CrearAlquilerUseCase(AlquilerRepositoryPort alquilerRepository,
                                AutoRepositoryPort autoRepository,
                                ClienteRepositoryPort clienteRepository) {
        this.alquilerRepository = alquilerRepository;
        this.autoRepository = autoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Alquiler crear(Long clienteId, Long autoId, int cantidadDias) {
        clienteRepository.obtenerPorId(clienteId);
        Auto auto = autoRepository.obtenerPorId(autoId);

        Alquiler alquiler = Alquiler.iniciar(clienteId, auto, cantidadDias, LocalDate.now());

        autoRepository.guardar(auto);                 // el auto queda ALQUILADO
        return alquilerRepository.guardar(alquiler);  // se persiste el alquiler ACTIVO
    }
}
