package com.condosaas.api.module.rol.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class RolRequestDTO {

    @NotBlank
    private String nombre;

    private String descripcion;
}