package com.autorent.infrastructure.adapter.input.rest;

import com.autorent.application.service.RegistrarAutoService;
import com.autorent.application.service.ConsultarAutoService;
import com.autorent.application.service.ConsultarAutosDisponiblesService;
import com.autorent.domain.exception.AutoNoEncontradoException;
import com.autorent.domain.exception.PlacaRepetidaException;
import com.autorent.domain.model.Auto;
import com.autorent.infrastructure.adapter.input.rest.dto.AutoRequest;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * ADAPTADOR DE ENTRADA (REST). Solo recibe HTTP y delega en las interfaces del service.
 * Flujo: AutoController -> interfaz (service) -> caso de uso (port/input).
 */
@RestController
@RequestMapping("/api/autos")
public class AutoController {

    private final RegistrarAutoService registrarAuto;
    private final ConsultarAutoService consultarAuto;
    private final ConsultarAutosDisponiblesService consultarDisponibles;

    public AutoController(RegistrarAutoService registrarAuto,
                          ConsultarAutoService consultarAuto,
                          ConsultarAutosDisponiblesService consultarDisponibles) {
        this.registrarAuto = registrarAuto;
        this.consultarAuto = consultarAuto;
        this.consultarDisponibles = consultarDisponibles;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Auto crear(@Valid @RequestBody AutoRequest request) {
        return registrarAuto.registrar(request.aDominio());
    }

    @GetMapping("/{id}")
    public Auto consultarPorId(@PathVariable Long id) {
        return consultarAuto.consultarPorId(id);
    }

    @GetMapping("/disponibles")
    public List<Auto> consultarDisponibles() {
        return consultarDisponibles.consultarDisponibles();
    }

    // ---------- Traducción de las excepciones de autos a HTTP ----------

    @ExceptionHandler(AutoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> noEncontrado(AutoNoEncontradoException ex) {
        return RespuestaError.de(HttpStatus.NOT_FOUND, ex.getMessage());          // 404
    }

    @ExceptionHandler(PlacaRepetidaException.class)
    public ResponseEntity<Map<String, Object>> placaRepetida(PlacaRepetidaException ex) {
        return RespuestaError.de(HttpStatus.CONFLICT, ex.getMessage());           // 409
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
