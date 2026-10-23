package com.autorent.infrastructure.adapter.input.rest;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Página de bienvenida: confirma que la API está activa y muestra cómo entrar a H2. */
@RestController
public class InicioController {

    @GetMapping("/")
    public Map<String, Object> inicio() {
        Map<String, Object> consolaH2 = new LinkedHashMap<>();
        consolaH2.put("url", "http://localhost:8080/h2-console");
        consolaH2.put("driverClass", "org.h2.Driver");
        consolaH2.put("jdbcUrl", "jdbc:h2:mem:autorent");
        consolaH2.put("usuario", "sa");
        consolaH2.put("contrasena", "(dejar vacío)");

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("aplicacion", "AutoRent - Sistema de Alquiler de Autos");
        respuesta.put("estado", "En ejecución");
        respuesta.put("autosDisponibles", "/api/autos/disponibles");
        respuesta.put("clientes", "/api/clientes");
        respuesta.put("alquileres", "/api/alquileres");
        respuesta.put("datosPrecargados", "auto 1 (Mazda KLM456), cliente 1 (Laura Gómez), alquiler 1 (5 días, ACTIVO)");
        respuesta.put("consolaH2", consolaH2);
        return respuesta;
    }
}
