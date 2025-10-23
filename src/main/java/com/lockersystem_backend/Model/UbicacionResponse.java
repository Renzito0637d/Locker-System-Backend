package com.lockersystem_backend.Model;

public class UbicacionResponse {
    private Long id;
    private String nombreEdificio;
    private String piso;
    private String pabellon;
    private String descripcion;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreEdificio() { return nombreEdificio; }
    public void setNombreEdificio(String nombreEdificio) { this.nombreEdificio = nombreEdificio; }

    public String getPiso() { return piso; }
    public void setPiso(String piso) { this.piso = piso; }

    public String getPabellon() { return pabellon; }
    public void setPabellon(String pabellon) { this.pabellon = pabellon; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
