package com.condosaas.api.module.torre.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

@Getter
@Setter
@Builder
@JsonPropertyOrder({ "id", "nombre", "condominioId", "condominioNombre" })
public class TorreResponseDTO {

    private Long id;
    private String nombre;
    private Long condominioId;
    private String condominioNombre;
}
