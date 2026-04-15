package com.dsw01.practica02.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EmpleadoUpdateRequest {

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotBlank
    @Size(max = 100)
    private String direccion;

    @NotBlank
    @Size(max = 100)
    private String telefono;

    @NotNull
    private Long version;

    @Size(max = 100)
    private String departamentoClave;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getDepartamentoClave() {
        return departamentoClave;
    }

    public void setDepartamentoClave(String departamentoClave) {
        this.departamentoClave = departamentoClave;
    }
}
