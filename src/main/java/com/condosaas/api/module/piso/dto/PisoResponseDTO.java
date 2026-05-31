package com.condosaas.api.module.piso.dto;

import com.condosaas.api.module.piso.model.EstadoPiso;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

@Getter
@Setter
@Builder
@JsonPropertyOrder({ "id", "numero", "condominioId", "torreId", "torreNombre", "estado" })
public class PisoResponseDTO {

    private Long id;
    private Integer numero;
    private EstadoPiso estado;

    private Long torreId;
    private String torreNombre;

    private Long condominioId;
}