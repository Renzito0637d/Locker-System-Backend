package com.lockersystem_backend.Entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "locker")
public class Locker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_locker")
    private Long id;

    @Column(name = "numero_locker", nullable = false, length = 20)
    private String numeroLocker;
    
    @Column(name = "estado", length = 50)
    private String estado;

    // Relación: Muchos lockers pertenecen a una ubicación
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ubicacion", nullable = false)
    private Ubicacion ubicacion;

    // Relación: Un locker puede tener muchas reservas
    @OneToMany(mappedBy = "locker", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Reserva> reservas;

    // Relación: Un locker puede tener muchos reportes
    @OneToMany(mappedBy = "locker", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Reporte> reportes;

    public Locker() {
    }

    public Locker(Long id, String numeroLocker, String estado, Ubicacion ubicacion, Set<Reserva> reservas, Set<Reporte> reportes) {
        this.id = id;
        this.numeroLocker = numeroLocker;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.reservas = reservas;
        this.reportes = reportes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroLocker() {
        return numeroLocker;
    }

    public void setNumeroLocker(String numeroLocker) {
        this.numeroLocker = numeroLocker;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Set<Reporte> getReportes() {
        return reportes;
    }

    public void setReportes(Set<Reporte> reportes) {
        this.reportes = reportes;
    }
    
}