package com.bedu.ProyectoFinalHsbcBedu.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "direcciones")
public class DireccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calle;

    private String numeroInterior;

    private String numeroExterior;

    private String ciudad;

    private String estado;

    private int codigoPostal;

    private String colonia;

    private String referencias;
}
