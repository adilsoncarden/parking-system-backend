package com.condosaas.api.module.piso.dto;

import com.condosaas.api.module.piso.model.EstadoPiso;
import lombok.*;

@Getter
@Setter
@Builder
public class PisoResponseDTO {

    private Long id;
    private Integer numero;
    private EstadoPiso estado;

    private Long torreId;
    private String torreNombre;

    private Long condominioId;
}