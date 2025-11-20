package com.lockersystem_backend.Model.ReservaDTOs;

import java.time.LocalDateTime; // <- IMPORT NECESARIO

public class ReservaRequest {

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estadoReserva;
    private Long userId;
    private Long lockerId;

    public ReservaRequest() {
    }

    public ReservaRequest(LocalDateTime fechaInicio, LocalDateTime fechaFin, String estadoReserva, Long userId, Long lockerId) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoReserva = estadoReserva;
        this.userId = userId;
        this.lockerId = lockerId;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLockerId() {
        return lockerId;
    }

    public void setLockerId(Long lockerId) {
        this.lockerId = lockerId;
    }
}
