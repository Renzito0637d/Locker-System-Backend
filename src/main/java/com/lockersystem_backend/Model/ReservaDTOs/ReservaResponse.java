package com.lockersystem_backend.Model.ReservaDTOs;

import java.time.LocalDateTime;

import com.lockersystem_backend.Entity.Reserva;

public class ReservaResponse {

    private Long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estadoReserva;

    // En lugar de IDs sueltos, usamos objetos anidados
    private UserDto user;
    private LockerDto locker;

    public ReservaResponse(Reserva reserva) {
        this.id = reserva.getId();
        this.fechaInicio = reserva.getFechaInicio();
        this.fechaFin = reserva.getFechaFin();
        // Convertimos el Enum a String para el JSON
        this.estadoReserva = reserva.getEstadoReserva() != null ? reserva.getEstadoReserva().toString() : null;

        // Mapeamos manualmente para asegurar que los datos viajen
        if (reserva.getUser() != null) {
            this.user = new UserDto(
                    reserva.getUser().getId(),
                    reserva.getUser().getUserName(),
                    reserva.getUser().getEmail());
        }

        if (reserva.getLocker() != null) {
            // Aquí es importante acceder a ubicacion también
            String pabellon = (reserva.getLocker().getUbicacion() != null)
                    ? reserva.getLocker().getUbicacion().getPabellon()
                    : "?";

            String piso = (reserva.getLocker().getUbicacion() != null)
                    ? reserva.getLocker().getUbicacion().getPiso()
                    : "?";

            this.locker = new LockerDto(
                    reserva.getLocker().getId(),
                    reserva.getLocker().getNumeroLocker(),
                    pabellon,
                    piso);
        }
    }

    // --- MINI CLASES STATICAS (DTOs internos) ---
    public static class UserDto {
        public Long id;
        public String userName;
        public String email;

        public UserDto(Long id, String userName, String email) {
            this.id = id;
            this.userName = userName;
            this.email = email;
        }
    }

    public static class LockerDto {
        public Long id;
        public String numeroLocker;
        public UbicacionDto ubicacion; // Anidamos un nivel más para respetar tu frontend

        public LockerDto(Long id, String numero, String pabellon, String piso) {
            this.id = id;
            this.numeroLocker = numero;
            this.ubicacion = new UbicacionDto(pabellon, piso);
        }
    }

    public static class UbicacionDto {
        public String pabellon;
        public String piso;

        public UbicacionDto(String pabellon, String piso) {
            this.pabellon = pabellon;
            this.piso = piso;
        }
    }

    // --- GETTERS ---
    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public UserDto getUser() {
        return user;
    }

    public LockerDto getLocker() {
        return locker;
    }
}