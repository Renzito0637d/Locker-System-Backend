package com.lockersystem_backend.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lockersystem_backend.Entity.Ubicacion;
import com.lockersystem_backend.Service.Interfaces.UbicacionService;
import com.lockersystem_backend.Model.UbicacionDTOs.UbicacionRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ubicaciones")
public class UbicacionController {

    @Autowired
    private UbicacionService ubicacionService;

    @GetMapping
    public ResponseEntity<List<Ubicacion>> listarUbicaciones() {
        return ResponseEntity.ok(ubicacionService.listarUbicaciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ubicacion> obtenerPorId(@PathVariable Long id) {
        return ubicacionService.obtenerUbicacionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> registrarUbicacion(@Valid @RequestBody UbicacionRequest request) {
        Ubicacion nueva = new Ubicacion();
        nueva.setNombreEdificio(request.getNombreEdificio());
        nueva.setPiso(request.getPiso());
        nueva.setPabellon(request.getPabellon());
        nueva.setDescripcion(request.getDescripcion());

        try {
            Ubicacion guardada = ubicacionService.registrarUbicacion(nueva);
            return ResponseEntity.ok(guardada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUbicacion(@PathVariable Long id, @Valid @RequestBody UbicacionRequest request) {
        Ubicacion actualizada = new Ubicacion();
        actualizada.setNombreEdificio(request.getNombreEdificio());
        actualizada.setPiso(request.getPiso());
        actualizada.setPabellon(request.getPabellon());
        actualizada.setDescripcion(request.getDescripcion());

        try {
            Ubicacion result = ubicacionService.actualizarUbicacion(id, actualizada);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUbicacion(@PathVariable Long id) {
        try {
            ubicacionService.eliminarUbicacion(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
