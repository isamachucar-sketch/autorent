package com.autorent.domain.model;

import com.autorent.domain.exception.AutoNoDisponibleException;
import java.math.BigDecimal;

/**
 * Modelo de dominio: un vehículo disponible para alquiler.
 * Java puro: no usa Spring, JPA ni Lombok (solo getters y setters).
 */
public class Auto {

    private Long id;
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private BigDecimal precioPorDia;
    private BigDecimal deposito;
    private EstadoAuto estado;

    // ---------- Reglas de negocio ----------

    public boolean estaDisponible() {
        return estado == EstadoAuto.DISPONIBLE;
    }

    /** Regla 1 y 2: solo un auto DISPONIBLE pasa a ALQUILADO. */
    public void alquilar() {
        if (!estaDisponible()) {
            throw new AutoNoDisponibleException(id, estado);
        }
        this.estado = EstadoAuto.ALQUILADO;
    }

    /** Regla 3: al terminar el alquiler el auto vuelve a DISPONIBLE. */
    public void liberar() {
        this.estado = EstadoAuto.DISPONIBLE;
    }

    // ---------- Getters y setters ----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
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

    public EstadoAuto getEstado() {
        return estado;
    }

    public void setEstado(EstadoAuto estado) {
        this.estado = estado;
    }
}
