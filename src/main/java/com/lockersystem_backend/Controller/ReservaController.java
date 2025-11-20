package com.lockersystem_backend.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lockersystem_backend.Entity.Reserva;
import com.lockersystem_backend.Model.ReservaDTOs.ReservaRequest;
import com.lockersystem_backend.Model.ReservaDTOs.ReservaResponse;
import com.lockersystem_backend.Service.Interfaces.ReservaService;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/")
    public List<ReservaResponse> listarReservas() {

        // 1. Obtén la lista de entidades (igual que antes)
        List<Reserva> listaDeEntidades = reservaService.listarReservas();

        // 2. Convierte la lista de Entidades a una lista de DTOs
        List<ReservaResponse> listaDeDTOs = listaDeEntidades.stream()
                .map(reserva -> new ReservaResponse(reserva)) // o .map(ReservaResponseDTO::new)
                .collect(Collectors.toList());

        // 3. Devuelve la lista de DTOs
        return listaDeDTOs;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> obtenerReserva(@PathVariable Long id) {

        // 1. Obtén el Optional<Reserva> (igual que antes)
        Optional<Reserva> reservaOptional = reservaService.obtenerReservaPorId(id);

        // 2. Mapea la Entidad a DTO, y luego el DTO a ResponseEntity
        return reservaOptional
                .map(reserva -> new ReservaResponse(reserva)) // Paso 1: Convierte Reserva -> ReservaResponseDTO
                .map(dto -> ResponseEntity.ok(dto)) // Paso 2: Envuelve el DTO en ResponseEntity.ok
                .orElseGet(() -> ResponseEntity.notFound().build()); // Si no existe, devuelve 404
    }

    @PostMapping("/")
    public ReservaResponse crearReserva(@RequestBody ReservaRequest request) {
        Reserva nuevaReservaGuardada = reservaService.crearReserva(request);
        return new ReservaResponse(nuevaReservaGuardada);
    }

    @PutMapping("/{id}")
    public ReservaResponse actualizarReserva(@PathVariable Long id, @RequestBody ReservaRequest request) {
        Reserva reservaActualizada = reservaService.actualizarReserva(id, request);
        return new ReservaResponse(reservaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
