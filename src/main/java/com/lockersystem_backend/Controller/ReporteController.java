package com.lockersystem_backend.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lockersystem_backend.Entity.Reporte;
import com.lockersystem_backend.Model.CreateReporteRequest;
import com.lockersystem_backend.Model.UpdateReporteRequest;
import com.lockersystem_backend.Model.ReporteResponse;
import com.lockersystem_backend.Service.Implements.ReporteServiceImpl;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteServiceImpl reporteService;

    @GetMapping("/")
    public List<ReporteResponse> getAll() {
        return reporteService.findAllResponses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponse> getById(@PathVariable Long id) {
        Optional<Reporte> r = reporteService.findById(id);
        return r.map(rep -> ResponseEntity.ok(reporteService.mapToResponse(rep)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReporteResponse> create(@RequestBody CreateReporteRequest dto) {
        Reporte created = reporteService.create(dto);
        return ResponseEntity.status(201).body(reporteService.mapToResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponse> update(@PathVariable Long id, @RequestBody UpdateReporteRequest dto) {
        var updated = reporteService.update(id, dto);
        return updated.map(rep -> ResponseEntity.ok(reporteService.mapToResponse(rep)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reporteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
