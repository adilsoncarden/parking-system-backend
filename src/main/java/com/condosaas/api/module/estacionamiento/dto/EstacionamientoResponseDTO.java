package com.condosaas.api.module.estacionamiento.dto;

import com.condosaas.api.module.estacionamiento.model.EstadoOcupacion;
import lombok.*;

@Getter
@Setter
@Builder
public class EstacionamientoResponseDTO {

    private Long id;
    private String codigo;
    private EstadoOcupacion estadoOcupacion;

    private Long zonaEstacionamientoId;
    private String zonaNombre;

    private Long condominioId;
}