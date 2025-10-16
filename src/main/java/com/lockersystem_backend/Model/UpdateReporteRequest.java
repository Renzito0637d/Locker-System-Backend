package com.lockersystem_backend.Model;

import java.time.LocalDateTime;

public class UpdateReporteRequest {
    private String descripcion;
    private LocalDateTime fechaReporte;
    private String tipoReporte;
    private Long userId;
    private Long lockerId;

    public UpdateReporteRequest() {}

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaReporte() { return fechaReporte; }
    public void setFechaReporte(LocalDateTime fechaReporte) { this.fechaReporte = fechaReporte; }

    public String getTipoReporte() { return tipoReporte; }
    public void setTipoReporte(String tipoReporte) { this.tipoReporte = tipoReporte; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getLockerId() { return lockerId; }
    public void setLockerId(Long lockerId) { this.lockerId=lockerId;}
}