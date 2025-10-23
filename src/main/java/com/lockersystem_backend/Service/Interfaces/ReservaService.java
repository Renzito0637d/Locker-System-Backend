package com.lockersystem_backend.Service.Interfaces;

import java.util.List;
import java.util.Optional;

import com.lockersystem_backend.Entity.Reserva;
import com.lockersystem_backend.Model.ReservaRequest;

public interface ReservaService {
    List<Reserva> listarReservas();
    Optional<Reserva> obtenerReservaPorId(Long id);
    Reserva crearReserva(ReservaRequest request);
    Reserva actualizarReserva(Long id, ReservaRequest request);
    void eliminarReserva(Long id);
}
