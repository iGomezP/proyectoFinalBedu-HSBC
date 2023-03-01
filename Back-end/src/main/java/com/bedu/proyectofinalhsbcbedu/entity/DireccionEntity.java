package com.bedu.proyectofinalhsbcbedu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
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
