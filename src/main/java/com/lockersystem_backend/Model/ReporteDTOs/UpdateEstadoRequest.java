package com.lockersystem_backend.Model.ReporteDTOs;

public class UpdateEstadoRequest {
    private String estado; // PENDIENTE, EN_PROCESO, RESUELTO
    private String nota;   // opcional: comentario del admin

    public UpdateEstadoRequest() {}

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }
}