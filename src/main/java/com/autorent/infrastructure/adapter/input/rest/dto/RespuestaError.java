package com.autorent.infrastructure.adapter.input.rest.dto;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

/**
 * Formato JSON de las respuestas de error del adaptador REST.
 * No es un manejador global: cada Controller decide qué excepción
 * convierte en 404, 409 o 400 y usa esta clase solo para armar el cuerpo.
 */
public final class RespuestaError {

    private RespuestaError() {
    }

    public static ResponseEntity<Map<String, Object>> de(HttpStatus status, String mensaje) {
        return ResponseEntity.status(status).body(cuerpo(status, mensaje));
    }

    /** 400 por @Valid: incluye el detalle de cada campo inválido. */
    public static ResponseEntity<Map<String, Object>> deValidacion(BindingResult resultado) {
        Map<String, String> errores = new LinkedHashMap<>();
        resultado.getFieldErrors()
                .forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        Map<String, Object> cuerpo = cuerpo(HttpStatus.BAD_REQUEST, "Los datos enviados no son válidos");
        cuerpo.put("errores", errores);
        return ResponseEntity.badRequest().body(cuerpo);
    }

    /** 400 por JSON mal formado o un tipo de dato inválido (ej. /api/autos/abc). */
    public static ResponseEntity<Map<String, Object>> deFormato() {
        return de(HttpStatus.BAD_REQUEST,
                "La solicitud tiene un formato incorrecto (JSON mal formado o tipo de dato inválido)");
    }

    private static Map<String, Object> cuerpo(HttpStatus status, String mensaje) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("timestamp", LocalDateTime.now().toString());
        cuerpo.put("status", status.value());
        cuerpo.put("error", status.getReasonPhrase());
        cuerpo.put("mensaje", mensaje);
        return cuerpo;
    }
}
