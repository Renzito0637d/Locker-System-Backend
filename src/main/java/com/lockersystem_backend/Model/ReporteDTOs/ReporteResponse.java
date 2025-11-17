package com.lockersystem_backend.Model.ReporteDTOs;

import java.time.LocalDateTime;

public class ReporteResponse {
    private Long id;
    private String descripcion;
    private String tipoReporte;
    private LocalDateTime fechaReporte;
    private String userName;
    private String numeroLocker;

    public ReporteResponse() {}

    public ReporteResponse(Long id, String descripcion, String tipoReporte, LocalDateTime fechaReporte, String userName, String numeroLocker) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipoReporte = tipoReporte;
        this.fechaReporte = fechaReporte;
        this.userName = userName;
        this.numeroLocker = numeroLocker;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipoReporte() { return tipoReporte; }
    public void setTipoReporte(String tipoReporte) { this.tipoReporte = tipoReporte; }

    public LocalDateTime getFechaReporte() { return fechaReporte; }
    public void setFechaReporte(LocalDateTime fechaReporte) { this.fechaReporte = fechaReporte; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getNumeroLocker() { return numeroLocker; }
    public void setNumeroLocker(String numeroLocker) { this.numeroLocker = numeroLocker; }
}
