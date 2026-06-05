package com.condosaas.api.module.piso.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

@Getter
@Setter
@Builder
@JsonPropertyOrder({ "id", "numero", "condominioId", "torreId", "torreNombre" })
public class PisoResponseDTO {

    private Long id;
    private Integer numero;

    private Long torreId;
    private String torreNombre;

    private Long condominioId;
}
