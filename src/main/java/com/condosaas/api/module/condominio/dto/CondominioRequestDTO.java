package com.condosaas.api.module.condominio.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class CondominioRequestDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String direccion;

    private String telefono;

    @Email
    private String email;
}
