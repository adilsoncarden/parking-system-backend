package com.condosaas.api.module.piso.dto;

import com.condosaas.api.module.piso.model.EstadoPiso;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class PisoRequestDTO {

    @NotNull
    private Integer numero;

    @NotNull
    private EstadoPiso estado;

    @NotNull
    private Long torreId;
}