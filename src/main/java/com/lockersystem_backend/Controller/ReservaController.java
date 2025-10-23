package com.lockersystem_backend.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lockersystem_backend.Entity.Reserva;
import com.lockersystem_backend.Model.ReservaRequest;
import com.lockersystem_backend.Service.Interfaces.ReservaService;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/")
    public List<Reserva> listarReservas() {
        return reservaService.listarReservas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReserva(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.obtenerReservaPorId(id);
        return reserva.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public Reserva crearReserva(@RequestBody ReservaRequest request) {
        return reservaService.crearReserva(request);
    }

    @PutMapping("/{id}")
    public Reserva actualizarReserva(@PathVariable Long id, @RequestBody ReservaRequest request) {
        return reservaService.actualizarReserva(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
