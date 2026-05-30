package com.condosaas.api.module.estacionamiento.dto;

import com.condosaas.api.module.estacionamiento.model.EstadoOcupacion;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class EstacionamientoRequestDTO {

    @NotBlank
    private String codigo;

    @NotNull
    private EstadoOcupacion estadoOcupacion;

    @NotNull
    private Long zonaEstacionamientoId;
}