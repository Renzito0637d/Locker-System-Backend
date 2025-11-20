package com.lockersystem_backend.Model.LockerDTOs;

public class CreateLockerRequest {
    private String numeroLocker;
    private String estado;
    private Long ubicacionId; // referencia a Ubicacion existente

    public CreateLockerRequest() {}

    public String getNumeroLocker() { return numeroLocker; }
    public void setNumeroLocker(String numeroLocker) { this.numeroLocker = numeroLocker; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Long getUbicacionId() { return ubicacionId; }
    public void setUbicacionId(Long ubicacionId) { this.ubicacionId = ubicacionId; }
}
