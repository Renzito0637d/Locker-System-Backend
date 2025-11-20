package com.lockersystem_backend.Service.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Reserva actualizarReserva(Long id, ReservaRequest request) {
        // 1. Buscamos la reserva existente
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // 2. Actualizamos fechas si vienen
        if (request.getFechaInicio() != null) reservaExistente.setFechaInicio(request.getFechaInicio());
        if (request.getFechaFin() != null) reservaExistente.setFechaFin(request.getFechaFin());

        // 3. Actualizamos referencias (User/Locker) solo si vienen IDs nuevos
        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            reservaExistente.setUser(user);
        }

        if (request.getLockerId() != null) {
            Locker locker = lockerRepository.findById(request.getLockerId())
                    .orElseThrow(() -> new RuntimeException("Locker no encontrado"));
            reservaExistente.setLocker(locker);
        }

        // 4. Actualizamos el ESTADO DE LA RESERVA y sincronizamos el LOCKER
        if (request.getEstadoReserva() != null) {
            String nuevoEstado = request.getEstadoReserva(); // Asumiendo String
            
            // Asignamos el nuevo estado a la reserva
            reservaExistente.setEstadoReserva(nuevoEstado);

            // Obtenemos el locker que tiene esta reserva (ya sea el antiguo o el nuevo asignado arriba)
            Locker lockerAsociado = reservaExistente.getLocker();
            
            if (lockerAsociado != null) {
                // Lógica automática:
                if ("APROBADA".equalsIgnoreCase(nuevoEstado)) {
                    lockerAsociado.setEstado("OCUPADO");
                } else if ("FINALIZADA".equalsIgnoreCase(nuevoEstado) || 
                           "CANCELADA".equalsIgnoreCase(nuevoEstado) || 
                           "RECHAZADA".equalsIgnoreCase(nuevoEstado)) {
                    lockerAsociado.setEstado("DISPONIBLE");
                }
                // Guardamos el cambio de estado en la tabla de lockers
                lockerRepository.save(lockerAsociado);
            }
        }

        // 5. Guardamos y retornamos la reserva
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
