package com.autorent.infrastructure.config;

import com.autorent.application.port.input.ConsultarAlquilerUseCase;
import com.autorent.application.port.input.ConsultarAutoUseCase;
import com.autorent.application.port.input.ConsultarAutosDisponiblesUseCase;
import com.autorent.application.port.input.ConsultarClienteUseCase;
import com.autorent.application.port.input.ConsultarHistorialClienteUseCase;
import com.autorent.application.port.input.CrearAlquilerUseCase;
import com.autorent.application.port.input.FinalizarAlquilerUseCase;
import com.autorent.application.port.input.RegistrarAutoUseCase;
import com.autorent.application.port.input.RegistrarClienteUseCase;
import com.autorent.application.port.output.AlquilerRepositoryPort;
import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.application.port.output.ClienteRepositoryPort;
import com.autorent.application.service.repository.AlquilerRepositoryService;
import com.autorent.application.service.repository.AutoRepositoryService;
import com.autorent.application.service.repository.ClienteRepositoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Aquí se "conecta" todo el hexágono, de la base de datos hacia el Controller:
 *   1. PUERTOS DE SALIDA (port/output): reciben la interfaz de salida del service
 *      (*RepositoryService), que Spring entrega con el adaptador JPA que la implementa.
 *   2. CASOS DE USO (port/input): reciben los puertos de salida que necesitan.
 *      Cada caso de uso implementa su interfaz de entrada (*Service), que es lo
 *      que reciben los Controllers.
 * Las clases de application no llevan @Service ni @Component: la infraestructura decide.
 * Si mañana se cambia H2 por MySQL, solo cambian los adaptadores; esta clase y el núcleo siguen igual.
 */
@Configuration
public class BeanConfiguration {

    // ---------------- Puertos de salida (clases con lógica) ----------------

    @Bean
    public AutoRepositoryPort autoRepositoryPort(AutoRepositoryService autoRepositoryService) {
        return new AutoRepositoryPort(autoRepositoryService);
    }

    @Bean
    public ClienteRepositoryPort clienteRepositoryPort(ClienteRepositoryService clienteRepositoryService) {
        return new ClienteRepositoryPort(clienteRepositoryService);
    }

    @Bean
    public AlquilerRepositoryPort alquilerRepositoryPort(AlquilerRepositoryService alquilerRepositoryService) {
        return new AlquilerRepositoryPort(alquilerRepositoryService);
    }

    // ---------------- Casos de uso de autos ----------------

    @Bean
    public RegistrarAutoUseCase registrarAutoUseCase(AutoRepositoryPort autoRepository) {
        return new RegistrarAutoUseCase(autoRepository);
    }

    @Bean
    public ConsultarAutoUseCase consultarAutoUseCase(AutoRepositoryPort autoRepository) {
        return new ConsultarAutoUseCase(autoRepository);
    }

    @Bean
    public ConsultarAutosDisponiblesUseCase consultarAutosDisponiblesUseCase(AutoRepositoryPort autoRepository) {
        return new ConsultarAutosDisponiblesUseCase(autoRepository);
    }

    // ---------------- Casos de uso de clientes ----------------

    @Bean
    public RegistrarClienteUseCase registrarClienteUseCase(ClienteRepositoryPort clienteRepository) {
        return new RegistrarClienteUseCase(clienteRepository);
    }

    @Bean
    public ConsultarClienteUseCase consultarClienteUseCase(ClienteRepositoryPort clienteRepository) {
        return new ConsultarClienteUseCase(clienteRepository);
    }

    @Bean
    public ConsultarHistorialClienteUseCase consultarHistorialClienteUseCase(ClienteRepositoryPort clienteRepository,
                                                                             AlquilerRepositoryPort alquilerRepository) {
        return new ConsultarHistorialClienteUseCase(clienteRepository, alquilerRepository);
    }

    // ---------------- Casos de uso de alquileres ----------------

    @Bean
    public CrearAlquilerUseCase crearAlquilerUseCase(AlquilerRepositoryPort alquilerRepository,
                                                     AutoRepositoryPort autoRepository,
                                                     ClienteRepositoryPort clienteRepository) {
        return new CrearAlquilerUseCase(alquilerRepository, autoRepository, clienteRepository);
    }

    @Bean
    public FinalizarAlquilerUseCase finalizarAlquilerUseCase(AlquilerRepositoryPort alquilerRepository,
                                                             AutoRepositoryPort autoRepository) {
        return new FinalizarAlquilerUseCase(alquilerRepository, autoRepository);
    }

    @Bean
    public ConsultarAlquilerUseCase consultarAlquilerUseCase(AlquilerRepositoryPort alquilerRepository) {
        return new ConsultarAlquilerUseCase(alquilerRepository);
    }
}
