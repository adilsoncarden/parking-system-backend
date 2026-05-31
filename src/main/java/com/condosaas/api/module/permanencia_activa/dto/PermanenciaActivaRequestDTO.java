package com.condosaas.api.module.permanencia_activa.dto;

import com.condosaas.api.module.permanencia_activa.model.EstadoPermanencia;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class PermanenciaActivaRequestDTO {

    @NotNull
    private LocalDateTime fechaEntrada;

    private LocalDateTime fechaSalida;

    @NotNull
    private EstadoPermanencia estado;

    @NotNull
    private Long vehiculoId;

    @NotNull
    private Long logEntradaId;

    private Long logSalidaId;
}