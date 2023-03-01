package com.bedu.proyectofinalhsbcbedu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoFunkoDTO {

    @JsonIgnore
    Long id;

    @NotEmpty(message = "El nombre del producto no puede estar vacío")
    private String name;

    @Positive(message = "El precio debe ser mayor que 0")
    private int price;

    @PositiveOrZero(message = "El valor no puede ser negativo")
    private int stock;

    @PositiveOrZero(message = "El valor no puede ser negativo")
    private int layaway;

    @Past(message = "No se puede añadir una fecha del pasado")
    private LocalDateTime createDate;

    @Past(message = "No se puede añadir una fecha del pasado")
    private LocalDateTime lastUpdate;

    private AwsImageFunkoDTO awsImageFunko;
}
