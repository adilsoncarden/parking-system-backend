package com.condosaas.api.module.torre.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class TorreRequestDTO {

    @NotBlank
    private String nombre;

    @NotNull
    private Long condominioId;
}
