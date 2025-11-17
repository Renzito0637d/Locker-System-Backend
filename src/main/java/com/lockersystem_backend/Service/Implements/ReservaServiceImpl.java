package com.lockersystem_backend.Service.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockersystem_backend.Entity.Reserva;
import com.lockersystem_backend.Entity.Locker;
import com.lockersystem_backend.Entity.User;
import com.lockersystem_backend.Model.ReservaDTOs.ReservaRequest;
import com.lockersystem_backend.Repository.ReservaRepository;
import com.lockersystem_backend.Repository.LockerRepository;
import com.lockersystem_backend.Repository.UserRepository;
import com.lockersystem_backend.Service.Interfaces.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LockerRepository lockerRepository;

    @Override
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public Optional<Reserva> obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    public Reserva crearReserva(ReservaRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Locker locker = lockerRepository.findById(request.getLockerId())
                .orElseThrow(() -> new RuntimeException("Locker no encontrado"));

        Reserva reserva = new Reserva();
        reserva.setUser(user);
        reserva.setLocker(locker);
        reserva.setFechaInicio(request.getFechaInicio());
        reserva.setFechaFin(request.getFechaFin());
        reserva.setEstadoReserva(request.getEstadoReserva());

        return reservaRepository.save(reserva);
    }

    @Override
    public Reserva actualizarReserva(Long id, ReservaRequest request) {
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Locker locker = lockerRepository.findById(request.getLockerId())
                .orElseThrow(() -> new RuntimeException("Locker no encontrado"));

        reservaExistente.setUser(user);
        reservaExistente.setLocker(locker);
        reservaExistente.setFechaInicio(request.getFechaInicio());
        reservaExistente.setFechaFin(request.getFechaFin());
        reservaExistente.setEstadoReserva(request.getEstadoReserva());

        return reservaRepository.save(reservaExistente);
    }

    @Override
    public void eliminarReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada");
        }
        reservaRepository.deleteById(id);
    }
}
