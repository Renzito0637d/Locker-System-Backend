package com.lockersystem_backend.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    // --- AQUÍ ESTÁ LA SOLUCIÓN AL BUCLE INFINITO ---
    // Convertimos cada entidad a DTO antes de responder
    @GetMapping
    public List<ReporteResponse> findAll() {
        return reporteService.findAll().stream()
                .map(ReporteResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponse> findById(@PathVariable Long id) {
        return reporteService.findById(id)
                .map(rep -> ResponseEntity.ok(new ReporteResponse(rep)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReporteResponse> create(@RequestBody CreateReporteRequest dto) {
        Reporte r = reporteService.create(dto);
        return ResponseEntity.ok(new ReporteResponse(r));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponse> update(@PathVariable Long id, @RequestBody UpdateReporteRequest dto) {
        return reporteService.update(id, dto)
                .map(r -> ResponseEntity.ok(new ReporteResponse(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reporteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
