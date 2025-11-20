package com.lockersystem_backend.Service.Interfaces;

import java.util.List;
import java.util.Optional;
import com.lockersystem_backend.Entity.Reporte;
import com.lockersystem_backend.Model.ReporteDTOs.CreateReporteRequest;
import com.lockersystem_backend.Model.ReporteDTOs.UpdateReporteRequest;

public interface ReporteService {
    List<Reporte> findAll();
    Optional<Reporte> findById(Long id);
    Reporte create(CreateReporteRequest dto);
    Optional<Reporte> update(Long id, UpdateReporteRequest dto);
    void deleteById(Long id);
}