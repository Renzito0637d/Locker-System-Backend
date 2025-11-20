package com.lockersystem_backend.Model.LockerDTOs;

import com.lockersystem_backend.Entity.Locker;
import com.lockersystem_backend.Entity.Ubicacion;

public class LockerResponse {
    private Long id;
    private String numeroLocker;
    private String estado;
    private String ubicacion; // nombre del edificio o texto que tú elijas

    public LockerResponse(Long id, String numeroLocker, String estado, String ubicacion) {
        this.id = id;
        this.numeroLocker = numeroLocker;
        this.estado = estado;
        this.ubicacion = ubicacion;
    }

    // getters
    public Long getId() {
        return id;
    }

    public String getNumeroLocker() {
        return numeroLocker;
    }

    public String getEstado() {
        return estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public static LockerResponse fromEntity(Locker locker) {
        Ubicacion u = locker.getUbicacion();

        // Formato simple: "A - 13"
        String ubicacionTexto = u.getPabellon() + " - " + u.getPiso();

        return new LockerResponse(
                locker.getId(),
                locker.getNumeroLocker(),
                locker.getEstado(),
                ubicacionTexto);
    }

}