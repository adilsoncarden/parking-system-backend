package com.condosaas.api.module.piso.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class PisoRequestDTO {

    @NotNull
    private Integer numero;

    @NotNull
    private Long torreId;
}
