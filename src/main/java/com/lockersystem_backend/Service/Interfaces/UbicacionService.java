package com.lockersystem_backend.Service.Interfaces;

import java.util.List;
import java.util.Optional;

import com.lockersystem_backend.Entity.Ubicacion;

public interface UbicacionService {

    List<Ubicacion> listarUbicaciones();
    Optional<Ubicacion> obtenerUbicacionPorId(Long id);
    Ubicacion registrarUbicacion(Ubicacion ubicacion);
    Ubicacion actualizarUbicacion(Long id, Ubicacion ubicacion);
    void eliminarUbicacion(Long id);
}
