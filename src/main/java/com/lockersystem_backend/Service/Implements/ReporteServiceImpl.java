package com.lockersystem_backend.Service.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lockersystem_backend.Entity.Reporte;
import com.lockersystem_backend.Entity.User;
import com.lockersystem_backend.Entity.Locker;
import com.lockersystem_backend.Model.CreateReporteRequest;
import com.lockersystem_backend.Model.UpdateReporteRequest;
import com.lockersystem_backend.Repository.LockerRepository;
import com.lockersystem_backend.Repository.ReporteRepository;
import com.lockersystem_backend.Repository.UserRepository;
import com.lockersystem_backend.Service.Interfaces.ReporteService;

@Service
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepository;
    private final UserRepository userRepository;
    private final LockerRepository lockerRepository;

    public ReporteServiceImpl(ReporteRepository reporteRepository, UserRepository userRepository, LockerRepository lockerRepository) {
        this.reporteRepository = reporteRepository;
        this.userRepository = userRepository;
        this.lockerRepository = lockerRepository;
    }

    @Override
    public List<Reporte> findAll() {
        return reporteRepository.findAll();
    }

    @Override
    public Optional<Reporte> findById(Long id) {
        return reporteRepository.findById(id);
    }

    @Override
    public Reporte create(CreateReporteRequest dto) {
        Reporte r = new Reporte();
        r.setDescripcion(dto.getDescripcion());
        r.setFechaReporte(java.time.LocalDateTime.now());
        r.setTipoReporte(dto.getTipoReporte());

        if (dto.getUserId() != null) {
            userRepository.findById(dto.getUserId()).ifPresent(r::setUser);
        }
        if (dto.getLockerId() != null) {
            lockerRepository.findById(dto.getLockerId()).ifPresent(r::setLocker);
        }

        return reporteRepository.save(r);
    }

    @Override
    public Optional<Reporte> update(Long id, UpdateReporteRequest dto) {
        var opt = reporteRepository.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        Reporte r = opt.get();
        if (dto.getDescripcion() != null) r.setDescripcion(dto.getDescripcion());
        if (dto.getFechaReporte() != null) r.setFechaReporte(dto.getFechaReporte());
        if (dto.getTipoReporte() != null) r.setTipoReporte(dto.getTipoReporte());
        if (dto.getUserId() != null) userRepository.findById(dto.getUserId()).ifPresent(r::setUser);
        if (dto.getLockerId() != null) lockerRepository.findById(dto.getLockerId()).ifPresent(r::setLocker);

        Reporte saved = reporteRepository.save(r);
        return Optional.of(saved);
    }

    @Override
    public void deleteById(Long id) {
        reporteRepository.deleteById(id);
}
}