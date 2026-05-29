package com.condosaas.api.module.zona_estacionamiento.dto;

import com.condosaas.api.module.zona_estacionamiento.model.EstadoZonaEstacionamiento;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class ZonaEstacionamientoRequestDTO {

    @NotBlank
    private String nombre;

    private String descripcion;

    @NotNull
    private EstadoZonaEstacionamiento estado;

    @NotNull
    private Long condominioId;
}