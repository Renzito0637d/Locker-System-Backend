package com.lockersystem_backend.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    private Long id;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "fecha_reporte", nullable = false)
    private LocalDateTime fechaReporte;
    
    @Column(name = "tipo_reporte", length = 100)
    private String tipoReporte;

    // Relación: Muchos reportes son creados por un usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference 
    private User user;
    

    // Relación: Muchos reportes se refieren a un locker
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_locker", nullable = false)
    @JsonBackReference
    private Locker locker;

    public Reporte() {
    }

    public Reporte(Long id, String descripcion, LocalDateTime fechaReporte, String tipoReporte, User user, Locker locker) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaReporte = fechaReporte;
        this.tipoReporte = tipoReporte;
        this.user = user;
        this.locker = locker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }
    
}