package com.lockersystem_backend.Service.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lockersystem_backend.Entity.Locker;
import com.lockersystem_backend.Entity.Ubicacion;
import com.lockersystem_backend.Model.CreateLockerRequest;
import com.lockersystem_backend.Model.UpdateLockerRequest;
import com.lockersystem_backend.Repository.LockerRepository;
import com.lockersystem_backend.Repository.UbicacionRepository;
import com.lockersystem_backend.Service.Interfaces.LockerService;

@Service
public class LockerServiceImpl implements LockerService {

    private final LockerRepository lockerRepository;
    private final UbicacionRepository ubicacionRepository;

    public LockerServiceImpl(LockerRepository lockerRepository, UbicacionRepository ubicacionRepository) {
        this.lockerRepository = lockerRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    @Override
    public List<Locker> findAll() {
        return lockerRepository.findAll();
    }

    @Override
    public Optional<Locker> findById(Long id) {
        return lockerRepository.findById(id);
    }

    @Override
    public Locker create(CreateLockerRequest dto) {
        Locker l = new Locker();
        l.setNumeroLocker(dto.getNumeroLocker());
        l.setEstado(dto.getEstado());
        if (dto.getUbicacionId() != null) {
            Optional<Ubicacion> u = ubicacionRepository.findById(dto.getUbicacionId());
            u.ifPresent(l::setUbicacion);
        }
        return lockerRepository.save(l);
    }

    @Override
    public Optional<Locker> update(Long id, UpdateLockerRequest dto) {
        var opt = lockerRepository.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        Locker l = opt.get();
        if (dto.getNumeroLocker() != null) l.setNumeroLocker(dto.getNumeroLocker());
        if (dto.getEstado() != null) l.setEstado(dto.getEstado());
        if (dto.getUbicacionId() != null) {
            Optional<Ubicacion> u = ubicacionRepository.findById(dto.getUbicacionId());
            u.ifPresent(l::setUbicacion);
        }
        Locker saved = lockerRepository.save(l);
        return Optional.of(saved);
    }

    @Override
    public void deleteById(Long id) {
        lockerRepository.deleteById(id);
    }
}
