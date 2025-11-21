package com.lockersystem_backend.Service.Interfaces;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.lockersystem_backend.Entity.Reporte;
import com.lockersystem_backend.Model.ReporteDTOs.CreateReporteRequest;
import com.lockersystem_backend.Model.ReporteDTOs.UpdateReporteRequest;
import com.lockersystem_backend.Model.ReporteDTOs.ReporteResponse;

public interface ReporteService {

    List<Reporte> findAll();
    Optional<Reporte> findById(Long id);

    Reporte create(CreateReporteRequest dto);
    Optional<Reporte> update(Long id, UpdateReporteRequest dto);

    void deleteById(Long id);

    // RF16: generar informe
    List<ReporteResponse> generarInforme(
            String estado,
            Long lockerId,
            Long userId,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    );

    // RF17: actualizar estado del reporte
    ReporteResponse actualizarEstado(Long id, String nuevoEstado, String nota);

    // AGREGADO: listado de respuestas ya mapeadas
    List<ReporteResponse> findAllResponses();

    // Agregado para evitar casteos
    ReporteResponse mapToResponse(Reporte r);
}
