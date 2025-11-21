package com.lockersystem_backend.Model.ReporteDTOs;

import com.lockersystem_backend.Entity.Enum.EstadoReporte;

public class UpdateReporteRequest {
    private String descripcion;
    private String tipoReporte;
    private String accionesTomadas; // Lo que llena el Admin
    private EstadoReporte estado; // Nuevo campo importante

    // Getters y Setters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getAccionesTomadas() {
        return accionesTomadas;
    }

    public void setAccionesTomadas(String accionesTomadas) {
        this.accionesTomadas = accionesTomadas;
    }

    public EstadoReporte getEstado() {
        return estado;
    }

    public void setEstado(EstadoReporte estado) {
        this.estado = estado;
    }
}