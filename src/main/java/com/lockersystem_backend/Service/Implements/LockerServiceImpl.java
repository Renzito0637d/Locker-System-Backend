package com.lockersystem_backend.Service.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired; // Agregamos Autowired si se usará en otros archivos

import com.lockersystem_backend.Entity.Locker;
import com.lockersystem_backend.Entity.Ubicacion;
import com.lockersystem_backend.Model.LockerDTOs.CreateLockerRequest;
import com.lockersystem_backend.Model.LockerDTOs.LockerResponse;
import com.lockersystem_backend.Model.LockerDTOs.UpdateLockerRequest;
import com.lockersystem_backend.Repository.LockerRepository;
import com.lockersystem_backend.Repository.UbicacionRepository;
import com.lockersystem_backend.Service.Interfaces.LockerService;

@Service
public class LockerServiceImpl implements LockerService {

    private final LockerRepository lockerRepository;
    private final UbicacionRepository ubicacionRepository;

    // Inyección por constructor (preferido por Spring)
    public LockerServiceImpl(LockerRepository lockerRepository, UbicacionRepository ubicacionRepository) {
        this.lockerRepository = lockerRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    // ----------------------------------------------------
    // 💡 MÉTODO FALTANTE PARA RF15 (BUSCAR POR CÓDIGO)
    // ----------------------------------------------------
    @Override
    public Optional<Locker> findByNumeroLocker(String numeroLocker) {
        // Asume que el método findByNumeroLocker está definido en LockerRepository.
        return lockerRepository.findByNumeroLocker(numeroLocker);
    }
    // ----------------------------------------------------
    
    private void validarEstado(String estado) {
        if (estado == null) return; 

        if (!estado.equalsIgnoreCase("disponible") &&
            !estado.equalsIgnoreCase("ocupado") &&
            !estado.equalsIgnoreCase("mantenimiento")) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Estado inválido. Solo se permite: disponible, ocupado o en mantenimiento"
            );
        }
    }


    @Override
    public List<LockerResponse> findAll() {
        return lockerRepository.findAll()
                .stream()
                .map(LockerResponse::fromEntity)
                .toList();
    }

    @Override
    public Optional<Locker> findById(Long id) {
        return lockerRepository.findById(id);
    }

    @Override
    public Locker create(CreateLockerRequest dto) {
        // 🔹 Validación: número de locker no vacío
        if (dto.getNumeroLocker() == null || dto.getNumeroLocker().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El número del locker es obligatorio");
        }

        // 🔹 Validación: no repetir número de locker (debes asegurarte que existsByNumeroLocker esté en el Repository)
        if (lockerRepository.existsByNumeroLocker(dto.getNumeroLocker())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El número de locker ya existe");
        }

        Locker l = new Locker();
        l.setNumeroLocker(dto.getNumeroLocker());
        validarEstado(dto.getEstado());
        l.setEstado(dto.getEstado());

        // 🔹 Validar que exista la ubicación
        if (dto.getUbicacionId() != null) {
            Ubicacion u = ubicacionRepository.findById(dto.getUbicacionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ubicación no encontrada"));
            l.setUbicacion(u);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La ubicación es obligatoria");
        }

        return lockerRepository.save(l);
    }

    @Override
    public Optional<Locker> update(Long id, UpdateLockerRequest dto) {
        Locker l = lockerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Locker no encontrado"));

        if (dto.getNumeroLocker() != null && !dto.getNumeroLocker().isEmpty()) {
            l.setNumeroLocker(dto.getNumeroLocker());
        }
        if (dto.getEstado() != null) {
            validarEstado(dto.getEstado());
            l.setEstado(dto.getEstado());
        }
        if (dto.getUbicacionId() != null) {
            Ubicacion u = ubicacionRepository.findById(dto.getUbicacionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ubicación no encontrada"));
            l.setUbicacion(u);
        }

        Locker saved = lockerRepository.save(l);
        return Optional.of(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!lockerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Locker no encontrado");
        }
        lockerRepository.deleteById(id);
    }
}