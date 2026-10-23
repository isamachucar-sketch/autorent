package com.autorent.domain.model;

import com.autorent.domain.exception.AlquilerYaFinalizadoException;
import com.autorent.domain.exception.CantidadDiasInvalidaException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Modelo de dominio: relación entre un cliente y un auto durante un período.
 * Aquí viven las reglas 4, 5 y 6 del negocio.
 */
public class Alquiler {

    private Long id;
    private Long clienteId;
    private Long autoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int cantidadDias;
    private BigDecimal valorTotal;
    private BigDecimal valorTotalConDeposito;
    private EstadoAlquiler estado;

    /**
     * Crea un alquiler ACTIVO para el auto indicado.
     * - Regla 5: los días deben ser mayores que cero.
     * - Regla 1 y 2: el auto debe estar DISPONIBLE y pasa a ALQUILADO.
     * - Regla 6: valorTotal = precioPorDia x cantidadDias (lo calcula el sistema).
     * - Adicional: valorTotalConDeposito = valorTotal + deposito del auto
     *   (lo que paga el cliente al recoger el auto; el depósito es una garantía).
     */
    public static Alquiler iniciar(Long clienteId, Auto auto, int cantidadDias, LocalDate fechaInicio) {
        if (cantidadDias <= 0) {
            throw new CantidadDiasInvalidaException(cantidadDias);
        }
        auto.alquilar();

        Alquiler alquiler = new Alquiler();
        alquiler.clienteId = clienteId;
        alquiler.autoId = auto.getId();
        alquiler.fechaInicio = fechaInicio;
        alquiler.fechaFin = fechaInicio.plusDays(cantidadDias);
        alquiler.cantidadDias = cantidadDias;
        alquiler.valorTotal = auto.getPrecioPorDia().multiply(BigDecimal.valueOf(cantidadDias));
        BigDecimal deposito = auto.getDeposito() != null ? auto.getDeposito() : BigDecimal.ZERO;
        alquiler.valorTotalConDeposito = alquiler.valorTotal.add(deposito);
        alquiler.estado = EstadoAlquiler.ACTIVO;
        return alquiler;
    }

    /** Regla 4: un alquiler no se puede finalizar dos veces. */
    public void finalizar() {
        if (estado == EstadoAlquiler.FINALIZADO) {
            throw new AlquilerYaFinalizadoException(id);
        }
        this.estado = EstadoAlquiler.FINALIZADO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getCantidadDias() {
        return cantidadDias;
    }

    public void setCantidadDias(int cantidadDias) {
        this.cantidadDias = cantidadDias;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorTotalConDeposito() {
        return valorTotalConDeposito;
    }

    public void setValorTotalConDeposito(BigDecimal valorTotalConDeposito) {
        this.valorTotalConDeposito = valorTotalConDeposito;
    }

    public EstadoAlquiler getEstado() {
        return estado;
    }

    public void setEstado(EstadoAlquiler estado) {
        this.estado = estado;
    }
}
