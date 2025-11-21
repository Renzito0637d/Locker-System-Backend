package com.lockersystem_backend.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lockersystem_backend.Entity.Reporte;
import com.lockersystem_backend.Model.ReporteDTOs.CreateReporteRequest;
import com.lockersystem_backend.Model.ReporteDTOs.UpdateReporteRequest;
import com.lockersystem_backend.Model.ReporteDTOs.ReporteResponse;
import com.lockersystem_backend.Service.Interfaces.ReporteService;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public List<Reporte> findAll() {
        return reporteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return reporteService.findById(id)
                .map(rep -> ResponseEntity.ok(reporteService.mapToResponse(rep)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReporteResponse> create(@RequestBody CreateReporteRequest dto) {
        Reporte r = reporteService.create(dto);
        return ResponseEntity.ok(reporteService.mapToResponse(r));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateReporteRequest dto) {
        return reporteService.update(id, dto)
                .map(r -> ResponseEntity.ok(reporteService.mapToResponse(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        reporteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ----------------------------------------
    // RF16 – Generar informe
    // ----------------------------------------
    @GetMapping("/informe")
    public List<ReporteResponse> generarInforme(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long lockerId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) LocalDateTime fechaInicio,
            @RequestParam(required = false) LocalDateTime fechaFin
    ) {
        return reporteService.generarInforme(estado, lockerId, userId, fechaInicio, fechaFin);
    }

    // ----------------------------------------
    // RF17 – Actualizar estado del reporte
    // ----------------------------------------
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado,
            @RequestParam(required = false) String nota
    ) {
        return ResponseEntity.ok(reporteService.actualizarEstado(id, nuevoEstado, nota));
    }
}
