package com.lockersystem_backend.Service.Implements;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Ubicacion registrarUbicacion(Ubicacion ubicacion) {
        boolean existe = ubicacionRepository.existsByNombreEdificioAndPisoAndPabellon(
            ubicacion.getNombreEdificio(),
            ubicacion.getPiso(),
            ubicacion.getPabellon()
        );

        if (existe) {
            throw new IllegalArgumentException("Ya existe una ubicación con esos datos");
        }

        return ubicacionRepository.save(ubicacion);
    }

    @Override
    @Transactional
    public Ubicacion actualizarUbicacion(Long id, Ubicacion ubicacion) {
        Optional<Ubicacion> ubicacionExistente = ubicacionRepository.findById(id);

        if (ubicacionExistente.isEmpty()) {
            throw new IllegalArgumentException("Ubicación con ID " + id + " no encontrada");
        }

        Ubicacion u = ubicacionExistente.get();
        u.setNombreEdificio(ubicacion.getNombreEdificio());
        u.setPiso(ubicacion.getPiso());
        u.setPabellon(ubicacion.getPabellon());
        u.setDescripcion(ubicacion.getDescripcion());

        return ubicacionRepository.save(u);
    }

    @Override
    @Transactional
    public void eliminarUbicacion(Long id) {
        if (!ubicacionRepository.existsById(id)) {
            throw new IllegalArgumentException("Ubicación con ID " + id + " no encontrada");
        }
        ubicacionRepository.deleteById(id);
    }
}
