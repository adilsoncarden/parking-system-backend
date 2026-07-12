package com.condosaas.api.module.inquilino_temporal.dto;

import com.condosaas.api.module.inquilino_temporal.model.EstadoInquilino;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class InquilinoTemporalRequestDTO {

    @NotBlank
    private String nombreCompleto;

    @NotBlank
    private String documento;

    private String telefono;

    @NotNull
    private LocalDateTime fechaInicio;

    @NotNull
    private LocalDateTime fechaFin;

    private EstadoInquilino estado;

    @NotNull
    private Long estacionamientoId;

    @NotNull
    private Long propietarioId;
}
