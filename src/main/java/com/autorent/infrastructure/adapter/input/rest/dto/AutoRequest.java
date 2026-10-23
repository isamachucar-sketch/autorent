package com.autorent.infrastructure.adapter.input.rest.dto;

import com.autorent.domain.model.Auto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/** Datos de entrada para registrar un auto. El estado NO se envía: lo decide el negocio. */
public class AutoRequest {

    @NotBlank(message = "La placa es obligatoria")
    private String placa;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "El año debe ser mayor o igual a 1900")
    private Integer anio;

    @NotNull(message = "El precio por día es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio por día debe ser mayor que cero")
    private BigDecimal precioPorDia;

    @NotNull(message = "El depósito es obligatorio")
    @PositiveOrZero(message = "El depósito no puede ser negativo")
    private BigDecimal deposito;

    public Auto aDominio() {
        Auto auto = new Auto();
        auto.setPlaca(placa.trim());
        auto.setMarca(marca.trim());
        auto.setModelo(modelo.trim());
        auto.setAnio(anio);
        auto.setPrecioPorDia(precioPorDia);
        auto.setDeposito(deposito);
        return auto;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public BigDecimal getPrecioPorDia() {
        return precioPorDia;
    }

    public void setPrecioPorDia(BigDecimal precioPorDia) {
        this.precioPorDia = precioPorDia;
    }

    public BigDecimal getDeposito() {
        return deposito;
    }

    public void setDeposito(BigDecimal deposito) {
        this.deposito = deposito;
    }
}
