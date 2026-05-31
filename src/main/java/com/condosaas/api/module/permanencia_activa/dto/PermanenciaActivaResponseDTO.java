package com.condosaas.api.module.permanencia_activa.dto;

import com.condosaas.api.module.permanencia_activa.model.EstadoPermanencia;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PermanenciaActivaResponseDTO {

    private Long id;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private EstadoPermanencia estado;

    private Long vehiculoId;
    private String placa;

    private Long logEntradaId;
    private Long logSalidaId;
}