package com.lockersystem_backend.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lockersystem_backend.Entity.Reporte;
import com.lockersystem_backend.Entity.Enum.EstadoReporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    List<Reporte> findByEstado(EstadoReporte estado);

    List<Reporte> findByLocker_Id(Long lockerId);

    List<Reporte> findByUser_Id(Long userId);

    List<Reporte> findByFechaReporteBetween(LocalDateTime start, LocalDateTime end);

    List<Reporte> findByEstadoAndLocker_Id(EstadoReporte estado, Long lockerId);

    // Si necesitas combinaciones más complejas, puedes usar @Query o Specification.
}