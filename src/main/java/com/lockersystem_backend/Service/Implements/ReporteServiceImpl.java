package com.lockersystem_backend.Service.Implements;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lockersystem_backend.Entity.Reporte;
import com.lockersystem_backend.Entity.User;
import com.lockersystem_backend.Entity.Locker;
import com.lockersystem_backend.Model.ReporteDTOs.CreateReporteRequest;
import org.springframework.transaction.annotation.Transactional;
import com.lockersystem_backend.Model.ReporteDTOs.ReporteResponse;
import com.lockersystem_backend.Model.ReporteDTOs.UpdateReporteRequest;
import com.lockersystem_backend.Repository.LockerRepository;
import com.lockersystem_backend.Repository.ReporteRepository;
import com.lockersystem_backend.Repository.UserRepository;
import com.lockersystem_backend.Service.Interfaces.ReporteService;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
@Transactional
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

        // Validar usuario
        if (dto.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId es obligatorio");
        }
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        r.setUser(user);

        // Validar locker
        if (dto.getLockerId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lockerId es obligatorio");
        }
        Locker locker = lockerRepository.findById(dto.getLockerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Locker no encontrado"));
        r.setLocker(locker);

        return reporteRepository.save(r);
    }

    @Override
    public Optional<Reporte> update(Long id, UpdateReporteRequest dto) {
        var opt = reporteRepository.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        Reporte r = opt.get();

        if (dto.getDescripcion() != null) r.setDescripcion(dto.getDescripcion());
        if (dto.getTipoReporte() != null) r.setTipoReporte(dto.getTipoReporte());
        if (dto.getFechaReporte() != null) {
            if (dto.getFechaReporte().isAfter(java.time.LocalDateTime.now())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha del reporte no puede ser futura");
            }
            r.setFechaReporte(dto.getFechaReporte());
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            r.setUser(user);
        }

        if (dto.getLockerId() != null) {
            Locker locker = lockerRepository.findById(dto.getLockerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Locker no encontrado"));
            r.setLocker(locker);
        }

        Reporte saved = reporteRepository.save(r);
        return Optional.of(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!reporteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reporte no encontrado");
        }
        reporteRepository.deleteById(id);
    }

    // Método opcional para mapear a DTO de respuesta
    public ReporteResponse mapToResponse(Reporte r) {
        return new ReporteResponse(
            r.getId(),
            r.getDescripcion(),
            r.getTipoReporte(),
            r.getFechaReporte(),
            r.getUser() != null ? r.getUser().getUserName() : null,
            r.getLocker() != null ? r.getLocker().getNumeroLocker() : null
        );
    }

    public List<ReporteResponse> findAllResponses() {
        return reporteRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
