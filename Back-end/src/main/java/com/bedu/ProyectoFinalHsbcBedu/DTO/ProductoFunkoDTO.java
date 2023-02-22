package com.bedu.ProyectoFinalHsbcBedu.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductoFunkoDTO {
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
    private LocalDateTime create_date;

    @Past(message = "No se puede añadir una fecha del pasado")
    private LocalDateTime last_update;
}
