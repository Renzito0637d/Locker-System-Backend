package com.lockersystem_backend.Model.ReporteDTOs;

import java.time.LocalDateTime;

import com.lockersystem_backend.Entity.Reporte;

public class ReporteResponse {
    private Long id;
    private String descripcion;
    private LocalDateTime fechaReporte;
    private String tipoReporte;
    private String estado;
    private String accionesTomadas;

    // Objetos anidados simplificados para romper el ciclo
    private SimpleUserDto user;
    private SimpleLockerDto locker;

    public ReporteResponse(Reporte reporte) {
        this.id = reporte.getId();
        this.descripcion = reporte.getDescripcion();
        this.fechaReporte = reporte.getFechaReporte();
        this.tipoReporte = reporte.getTipoReporte();
        this.estado = reporte.getEstado() != null ? reporte.getEstado().toString() : "PENDIENTE";
        this.accionesTomadas = reporte.getAccionesTomadas();

        if (reporte.getUser() != null) {
            this.user = new SimpleUserDto(
                    reporte.getUser().getId(),
                    reporte.getUser().getUserName(),
                    reporte.getUser().getEmail());
        }

        if (reporte.getLocker() != null) {
            String pabellon = "?";
            String piso = "?";
            if (reporte.getLocker().getUbicacion() != null) {
                pabellon = reporte.getLocker().getUbicacion().getPabellon();
                piso = reporte.getLocker().getUbicacion().getPiso();
            }
            this.locker = new SimpleLockerDto(
                    reporte.getLocker().getId(),
                    reporte.getLocker().getNumeroLocker(),
                    pabellon,
                    piso);
        }
    }

    // --- DTOs INTERNOS ---
    public static class SimpleUserDto {
        public Long id;
        public String userName;
        public String email;

        public SimpleUserDto(Long id, String userName, String email) {
            this.id = id;
            this.userName = userName;
            this.email = email;
        }
    }

    public static class SimpleLockerDto {
        public Long id;
        public String numeroLocker;
        public UbicacionDto ubicacion;

        public SimpleLockerDto(Long id, String numero, String pabellon, String piso) {
            this.id = id;
            this.numeroLocker = numero;
            this.ubicacion = new UbicacionDto(pabellon, piso);
        }
    }

    public static class UbicacionDto {
        public String pabellon;
        public String piso;

        public UbicacionDto(String p, String pi) {
            this.pabellon = p;
            this.piso = pi;
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public String getEstado() {
        return estado;
    }

    public String getAccionesTomadas() {
        return accionesTomadas;
    }

    public SimpleUserDto getUser() {
        return user;
    }

    public SimpleLockerDto getLocker() {
        return locker;
    }
}