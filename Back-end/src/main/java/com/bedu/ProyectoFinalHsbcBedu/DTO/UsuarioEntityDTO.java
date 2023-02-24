package com.bedu.proyectofinalhsbcbedu.dto;

import com.bedu.proyectofinalhsbcbedu.entity.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntityDTO {

    @JsonIgnore
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[A-z_].{6,12}",
            message = "El nombre no debe contener caracteres especiales," +
                    " debe tener mínimo 6 caracteres y máximo 12")
    private String name;

    @Email(message = "Se debe introducir un email válido")
    @NotEmpty(message = "El campo no puede ir vacío")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-+_!@#$%^&*.,?]).{8,}",
            message = "La contraseña debe contener al menos 1 mayúscula, 1 minúscula, 1 número, 1 símbolo y mínimo 8 caracteres")
    private String userPassword;

    @Enumerated(EnumType.STRING)
    private ERole rol;

    @NotNull
    DireccionEntityDTO direccion;
}
