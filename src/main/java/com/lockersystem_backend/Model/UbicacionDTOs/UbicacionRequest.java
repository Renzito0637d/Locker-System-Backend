package com.lockersystem_backend.Model.UbicacionDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UbicacionRequest {

    @NotBlank(message = "El nombre del edificio es obligatorio")
    @Size(max = 100, message = "El nombre del edificio no debe exceder 100 caracteres")
    private String nombreEdificio;

    @NotBlank(message = "El piso es obligatorio")
    private String piso;

    @NotBlank(message = "El pabellón es obligatorio")
    private String pabellon;

    private String descripcion;

    // Getters y Setters
    public String getNombreEdificio() { return nombreEdificio; }
    public void setNombreEdificio(String nombreEdificio) { this.nombreEdificio = nombreEdificio; }

    public String getPiso() { return piso; }
    public void setPiso(String piso) { this.piso = piso; }

    public String getPabellon() { return pabellon; }
    public void setPabellon(String pabellon) { this.pabellon = pabellon; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
