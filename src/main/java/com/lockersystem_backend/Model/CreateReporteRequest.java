package com.lockersystem_backend.Model;

import java.time.LocalDateTime;

public class CreateReporteRequest {
    private String descripcion;
    private String tipoReporte;
    private Long userId;
    private Long lockerId;

    public CreateReporteRequest() {}

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipoReporte() { return tipoReporte; }
    public void setTipoReporte(String tipoReporte) { this.tipoReporte = tipoReporte; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getLockerId() { return lockerId; }
    public void setLockerId(Long lockerId) { this.lockerId=lockerId;}
}