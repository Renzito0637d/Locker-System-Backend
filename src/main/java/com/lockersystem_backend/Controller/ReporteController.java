package com.lockersystem_backend.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lockersystem_backend.Entity.Reporte;
import com.lockersystem_backend.Model.CreateReporteRequest;
import com.lockersystem_backend.Model.UpdateReporteRequest;
import com.lockersystem_backend.Service.Interfaces.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/")
    public List<Reporte> getAll() {
        return reporteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> getById(@PathVariable Long id) {
        Optional<Reporte> r = reporteService.findById(id);
        return r.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Reporte> create(@RequestBody CreateReporteRequest dto) {
        Reporte created = reporteService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reporte> update(@PathVariable Long id, @RequestBody UpdateReporteRequest dto) {
        var updated = reporteService.update(id, dto);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (reporteService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        reporteService.deleteById(id);
        return ResponseEntity.noContent().build();}
}