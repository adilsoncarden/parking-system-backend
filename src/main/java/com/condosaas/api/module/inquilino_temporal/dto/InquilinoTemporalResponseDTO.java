package com.condosaas.api.module.inquilino_temporal.dto;

import com.condosaas.api.module.inquilino_temporal.model.EstadoInquilino;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class InquilinoTemporalResponseDTO {

    private Long id;
    private String nombreCompleto;
    private String documento;
    private String telefono;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private EstadoInquilino estado;

    private Long estacionamientoId;
    private String estacionamientoCodigo;

    private Long propietarioId;
    private String propietarioNombre;

    private Long condominioId;
}
