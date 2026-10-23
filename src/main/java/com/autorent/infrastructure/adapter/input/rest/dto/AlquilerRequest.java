package com.autorent.infrastructure.adapter.input.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Datos de entrada para crear un alquiler.
 * No existe el campo valorTotal: si el cliente lo envía, se ignora; lo calcula el negocio.
 */
public class AlquilerRequest {

    @NotNull(message = "El clienteId es obligatorio")
    private Long clienteId;

    @NotNull(message = "El autoId es obligatorio")
    private Long autoId;

    @NotNull(message = "La cantidad de días es obligatoria")
    @Positive(message = "La cantidad de días debe ser mayor que cero")
    private Integer cantidadDias;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    public Integer getCantidadDias() {
        return cantidadDias;
    }

    public void setCantidadDias(Integer cantidadDias) {
        this.cantidadDias = cantidadDias;
    }
}
