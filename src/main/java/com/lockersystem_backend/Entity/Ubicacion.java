package com.lockersystem_backend.Entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ubicacion")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private Long id;

    @Column(name = "nombre_edificio", length = 100)
    private String nombreEdificio;

    @Column(name = "piso", length = 20)
    private String piso;
    
    @Column(name = "pabellon", length = 1)
    private String pabellon;
    
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    // Relación: Una ubicación puede tener muchos lockers
    @OneToMany(mappedBy = "ubicacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Locker> lockers;

    public Ubicacion() {
    }

    public Ubicacion(Long id, String nombreEdificio, String piso, String pabellon, String descripcion, Set<Locker> lockers) {
        this.id = id;
        this.nombreEdificio = nombreEdificio;
        this.piso = piso;
        this.pabellon = pabellon;
        this.descripcion = descripcion;
        this.lockers = lockers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEdificio() {
        return nombreEdificio;
    }

    public void setNombreEdificio(String nombreEdificio) {
        this.nombreEdificio = nombreEdificio;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getPabellon() {
        return pabellon;
    }

    public void setPabellon(String pabellon) {
        this.pabellon = pabellon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Locker> getLockers() {
        return lockers;
    }

    public void setLockers(Set<Locker> lockers) {
        this.lockers = lockers;
    }
    
}