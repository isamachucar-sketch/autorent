package com.autorent.infrastructure.adapter.input.rest;

import com.autorent.application.service.CrearAlquilerService;
import com.autorent.application.service.FinalizarAlquilerService;
import com.autorent.application.service.ConsultarAlquilerService;
import com.autorent.domain.exception.AlquilerNoEncontradoException;
import com.autorent.domain.exception.AlquilerYaFinalizadoException;
import com.autorent.domain.exception.AutoNoDisponibleException;
import com.autorent.domain.exception.AutoNoEncontradoException;
import com.autorent.domain.exception.CantidadDiasInvalidaException;
import com.autorent.domain.exception.ClienteNoEncontradoException;
import com.autorent.domain.model.Alquiler;
import com.autorent.infrastructure.adapter.input.rest.dto.AlquilerRequest;
import com.autorent.infrastructure.adapter.input.rest.dto.RespuestaError;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * ADAPTADOR DE ENTRADA (REST) para alquileres.
 * Sin lógica de negocio: no busca autos, no valida disponibilidad, no calcula precios.
 */
@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private final CrearAlquilerService crearAlquiler;
    private final FinalizarAlquilerService finalizarAlquiler;
    private final ConsultarAlquilerService consultarAlquiler;

    public AlquilerController(CrearAlquilerService crearAlquiler,
                              FinalizarAlquilerService finalizarAlquiler,
                              ConsultarAlquilerService consultarAlquiler) {
        this.crearAlquiler = crearAlquiler;
        this.finalizarAlquiler = finalizarAlquiler;
        this.consultarAlquiler = consultarAlquiler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Alquiler crear(@Valid @RequestBody AlquilerRequest request) {
        return crearAlquiler.crear(request.getClienteId(), request.getAutoId(), request.getCantidadDias());
    }

    /** EXTRA: lista todos los alquileres. */
    @GetMapping
    public List<Alquiler> consultarTodos() {
        return consultarAlquiler.consultarTodos();
    }

    @GetMapping("/{id}")
    public Alquiler consultarPorId(@PathVariable Long id) {
        return consultarAlquiler.consultarPorId(id);
    }

    @PutMapping("/{id}/finalizar")
    public Alquiler finalizar(@PathVariable Long id) {
        return finalizarAlquiler.finalizar(id);
    }

    // ---------- Traducción de las excepciones del alquiler a HTTP ----------

    @ExceptionHandler({ClienteNoEncontradoException.class, AutoNoEncontradoException.class,
            AlquilerNoEncontradoException.class})
    public ResponseEntity<Map<String, Object>> noEncontrado(RuntimeException ex) {
        return RespuestaError.de(HttpStatus.NOT_FOUND, ex.getMessage());          // 404
    }

    @ExceptionHandler({AutoNoDisponibleException.class, AlquilerYaFinalizadoException.class})
    public ResponseEntity<Map<String, Object>> conflicto(RuntimeException ex) {
        return RespuestaError.de(HttpStatus.CONFLICT, ex.getMessage());           // 409
    }

    @ExceptionHandler(CantidadDiasInvalidaException.class)
    public ResponseEntity<Map<String, Object>> diasInvalidos(CantidadDiasInvalidaException ex) {
        return RespuestaError.de(HttpStatus.BAD_REQUEST, ex.getMessage());        // 400
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> datosInvalidos(MethodArgumentNotValidException ex) {
        return RespuestaError.deValidacion(ex.getBindingResult());                // 400
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Map<String, Object>> formatoInvalido() {
        return RespuestaError.deFormato();                                        // 400
    }
}
