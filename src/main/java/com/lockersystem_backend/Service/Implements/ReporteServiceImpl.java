package com.lockersystem_backend.Service.Implements;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockersystem_backend.Entity.Enum.EstadoReporte;
import com.lockersystem_backend.Entity.Reporte;
import com.lockersystem_backend.Entity.User;
import com.lockersystem_backend.Entity.Locker;
import com.lockersystem_backend.Repository.ReporteRepository;
import com.lockersystem_backend.Repository.UserRepository;
import com.lockersystem_backend.Repository.LockerRepository;
import com.lockersystem_backend.Service.Interfaces.ReporteService;
import com.lockersystem_backend.Model.ReporteDTOs.CreateReporteRequest;
import com.lockersystem_backend.Model.ReporteDTOs.UpdateReporteRequest;
@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LockerRepository lockerRepository;

    // ----------------------------------------
    // CRUD BÁSICO
    // ----------------------------------------
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
        // Manejo de errores simplificado con orElseThrow()
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        Locker locker = lockerRepository.findById(dto.getLockerId()).orElseThrow();

        Reporte reporte = new Reporte();
        reporte.setDescripcion(dto.getDescripcion());
        reporte.setTipoReporte(dto.getTipoReporte());
        reporte.setFechaReporte(LocalDateTime.now());
        reporte.setEstado(EstadoReporte.PENDIENTE);
        reporte.setUser(user);
        reporte.setLocker(locker);

        return reporteRepository.save(reporte);
    }

    @Override
    public Optional<Reporte> update(Long id, UpdateReporteRequest dto) {
        return reporteRepository.findById(id).map(r -> {
            // Solo actualizamos si el dato NO es nulo ni vacío
            // Así evitamos borrar la descripción original si el admin solo manda acciones
            if (dto.getDescripcion() != null && !dto.getDescripcion().isEmpty()) {
                r.setDescripcion(dto.getDescripcion());
            }

            if (dto.getTipoReporte() != null && !dto.getTipoReporte().isEmpty()) {
                r.setTipoReporte(dto.getTipoReporte());
            }

            // Campo crítico para el ADMIN
            if (dto.getAccionesTomadas() != null) {
                r.setAccionesTomadas(dto.getAccionesTomadas());
            }

            // Actualizar estado (Ej: Pasar de PENDIENTE a RESUELTO)
            if (dto.getEstado() != null) {
                r.setEstado(dto.getEstado());
            }

            return reporteRepository.save(r);
        });
    }

    @Override
    public void deleteById(Long id) {
        reporteRepository.deleteById(id);
    }

}