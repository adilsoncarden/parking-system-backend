package com.condosaas.api.module.permiso.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class PermisoRequestDTO {

    @NotBlank
    private String nombre;
}
