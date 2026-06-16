package com.condosaas.api.module.entrada.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class EntradaRequestDTO {

    @NotBlank
    private String nombre;

    @PositiveOrZero
    private Integer capacidadCarritos;

    @NotNull
    private Long condominioId;
}
