package com.autorent.infrastructure.adapter.input.rest;

import com.autorent.application.service.RegistrarClienteService;
import com.autorent.application.service.ConsultarClienteService;
import com.autorent.application.service.ConsultarHistorialClienteService;
import com.autorent.domain.exception.ClienteNoEncontradoException;
import com.autorent.domain.exception.DocumentoRepetidoException;
import com.autorent.domain.model.Alquiler;
import com.autorent.domain.model.Cliente;
import com.autorent.infrastructure.adapter.input.rest.dto.ClienteRequest;
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

/** ADAPTADOR DE ENTRADA (REST) para clientes. Delega en las interfaces del service. */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final RegistrarClienteService registrarCliente;
    private final ConsultarClienteService consultarCliente;
    private final ConsultarHistorialClienteService consultarHistorial;

    public ClienteController(RegistrarClienteService registrarCliente,
                             ConsultarClienteService consultarCliente,
                             ConsultarHistorialClienteService consultarHistorial) {
        this.registrarCliente = registrarCliente;
        this.consultarCliente = consultarCliente;
        this.consultarHistorial = consultarHistorial;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente crear(@Valid @RequestBody ClienteRequest request) {
        return registrarCliente.registrar(request.aDominio());
    }

    /** EXTRA: lista todos los clientes. */
    @GetMapping
    public List<Cliente> consultarTodos() {
        return consultarCliente.consultarTodos();
    }

    @GetMapping("/{id}")
    public Cliente consultarPorId(@PathVariable Long id) {
        return consultarCliente.consultarPorId(id);
    }

    /** PLUS: historial de alquileres del cliente. */
    @GetMapping("/{id}/alquileres")
    public List<Alquiler> historial(@PathVariable Long id) {
        return consultarHistorial.consultarHistorial(id);
    }

    // ---------- Traducción de las excepciones de clientes a HTTP ----------

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> noEncontrado(ClienteNoEncontradoException ex) {
        return RespuestaError.de(HttpStatus.NOT_FOUND, ex.getMessage());          // 404
    }

    @ExceptionHandler(DocumentoRepetidoException.class)
    public ResponseEntity<Map<String, Object>> documentoRepetido(DocumentoRepetidoException ex) {
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
