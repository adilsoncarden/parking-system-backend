package com.condosaas.api.module.torre.dto;

import com.condosaas.api.module.torre.model.EstadoTorre;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class TorreRequestDTO {

    @NotBlank
    private String nombre;

    @NotNull
    private EstadoTorre estado;

    @NotNull
    private Long condominioId;
}