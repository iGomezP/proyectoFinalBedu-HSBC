package com.bedu.proyectofinalhsbcbedu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DireccionEntityDTO {

    @NotNull
    private Long id;

    @NotNull
    @NotEmpty(message = "No debe ir vacío")
    private String calle;

    @NotNull
    @NotEmpty(message = "No debe ir vacío")
    private String numeroInterior;

    @NotNull
    @NotEmpty(message = "No debe ir vacío")
    private String numeroExterior;

    @NotNull
    private String ciudad;

    @NotNull
    private String estado;

    @NotNull
    private int codigoPostal;

    @NotNull
    private String colonia;

    @NotNull
    private String referencias;
}
