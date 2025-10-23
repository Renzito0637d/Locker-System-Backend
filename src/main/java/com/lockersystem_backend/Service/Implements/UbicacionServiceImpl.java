package com.lockersystem_backend.Service.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockersystem_backend.Entity.Ubicacion;
import com.lockersystem_backend.Repository.UbicacionRepository;
import com.lockersystem_backend.Service.Interfaces.UbicacionService;

@Service
public class UbicacionServiceImpl implements UbicacionService {

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Override
    public List<Ubicacion> listarUbicaciones() {
        return ubicacionRepository.findAll();
    }

    @Override
    public Optional<Ubicacion> obtenerUbicacionPorId(Long id) {
        return ubicacionRepository.findById(id);
    }

    @Override
    public Ubicacion registrarUbicacion(Ubicacion ubicacion) {
        return ubicacionRepository.save(ubicacion);
    }

    @Override
    public Ubicacion actualizarUbicacion(Long id, Ubicacion ubicacion) {
        Optional<Ubicacion> ubicacionExistente = ubicacionRepository.findById(id);
        if (ubicacionExistente.isPresent()) {
            Ubicacion u = ubicacionExistente.get();
            u.setNombreEdificio(ubicacion.getNombreEdificio());
            u.setPiso(ubicacion.getPiso());
            u.setPabellon(ubicacion.getPabellon());
            u.setDescripcion(ubicacion.getDescripcion());
            return ubicacionRepository.save(u);
        } else {
            throw new RuntimeException("Ubicación con ID " + id + " no encontrada");
        }
    }

    @Override
    public void eliminarUbicacion(Long id) {
        if (ubicacionRepository.existsById(id)) {
            ubicacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se puede eliminar. Ubicación con ID " + id + " no encontrada");
        }
    }
}
