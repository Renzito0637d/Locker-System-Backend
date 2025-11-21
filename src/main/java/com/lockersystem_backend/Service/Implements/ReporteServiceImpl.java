package com.lockersystem_backend.Service.Implements;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.lockersystem_backend.Model.ReporteDTOs.ReporteResponse;

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

    // ----------------------------------------
    // AGREGADO: Listado completo mapeado (Requerido por la interfaz)
    // ----------------------------------------
    /**
     * @Override: ESTE MÉTODO FALTABA Y CAUSABA EL ERROR DE COMPILACIÓN.
     *            Recupera todos los reportes, los mapea a ReporteResponse y los
     *            retorna.
     */
    @Override
    public List<ReporteResponse> findAllResponses() {
        // Usa findAll() para obtener las entidades y luego mapearlas.
        return reporteRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ----------------------------------------
    // RF16 – Generar informe con filtros
    // ----------------------------------------
    @Override
    public List<ReporteResponse> generarInforme(String estado, Long lockerId, Long userId,
            LocalDateTime fechaInicio, LocalDateTime fechaFin) {

        List<Reporte> lista = reporteRepository.findAll();

        return lista.stream()
                .filter(r -> estado == null || r.getEstado().name().equalsIgnoreCase(estado))
                .filter(r -> lockerId == null || r.getLocker().getId().equals(lockerId))
                .filter(r -> userId == null || r.getUser().getId().equals(userId))
                // NOTA: Usé isAfter para fechaInicio y isBefore para fechaFin (exclusivo)
                // Si quieres inclusivo, usa isAfter(fechaInicio) ||
                // r.getFechaReporte().isEqual(fechaInicio)
                .filter(r -> fechaInicio == null || r.getFechaReporte().isAfter(fechaInicio))
                .filter(r -> fechaFin == null || r.getFechaReporte().isBefore(fechaFin))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ----------------------------------------
    // RF17 – Cambiar estado del reporte
    // ----------------------------------------
    @Override
    public ReporteResponse actualizarEstado(Long id, String nuevoEstado, String nota) {
        Reporte r = reporteRepository.findById(id).orElseThrow();

        // Asegura que el enum exista antes de guardar.
        r.setEstado(EstadoReporte.valueOf(nuevoEstado.toUpperCase()));

        // Si necesitas guardar la nota, debes tener un campo `nota` en la entidad
        // Reporte
        // r.setNota(nota);

        return mapToResponse(reporteRepository.save(r));
    }

    // ----------------------------------------
    // Conversión a DTO
    // ----------------------------------------
    @Override
    public ReporteResponse mapToResponse(Reporte r) {
        return new ReporteResponse(
                r.getId(),
                r.getDescripcion(),
                r.getTipoReporte(),
                r.getFechaReporte(),
                r.getUser() != null ? r.getUser().getUserName() : null,
                r.getLocker() != null ? r.getLocker().getNumeroLocker() : null,
                r.getEstado() != null ? r.getEstado().name() : null);
    }
}