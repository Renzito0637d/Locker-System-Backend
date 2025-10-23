package com.lockersystem_backend.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lockersystem_backend.Entity.Ubicacion;
import com.lockersystem_backend.Service.Interfaces.UbicacionService;

@RestController
@RequestMapping("/api/ubicaciones")
@CrossOrigin(origins = "*")
public class UbicacionController {

    @Autowired
    private UbicacionService ubicacionService;

    // 📄 Listar todas las ubicaciones
    @GetMapping
    public ResponseEntity<List<Ubicacion>> listarUbicaciones() {
        return ResponseEntity.ok(ubicacionService.listarUbicaciones());
    }

    // 🔍 Obtener ubicación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ubicacion> obtenerUbicacion(@PathVariable Long id) {
        Optional<Ubicacion> ubicacion = ubicacionService.obtenerUbicacionPorId(id);
        return ubicacion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ➕ Registrar nueva ubicación
    @PostMapping
    public ResponseEntity<Ubicacion> registrarUbicacion(@RequestBody Ubicacion ubicacion) {
        return ResponseEntity.ok(ubicacionService.registrarUbicacion(ubicacion));
    }

    // ✏️ Actualizar ubicación existente
    @PutMapping("/{id}")
    public ResponseEntity<Ubicacion> actualizarUbicacion(@PathVariable Long id, @RequestBody Ubicacion ubicacion) {
        return ResponseEntity.ok(ubicacionService.actualizarUbicacion(id, ubicacion));
    }

    // 🗑️ Eliminar ubicación
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUbicacion(@PathVariable Long id) {
        ubicacionService.eliminarUbicacion(id);
        return ResponseEntity.ok("Ubicación eliminada correctamente");
    }
}
