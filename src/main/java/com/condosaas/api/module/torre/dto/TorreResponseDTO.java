package com.condosaas.api.module.torre.dto;

import com.condosaas.api.module.torre.model.EstadoTorre;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

@Getter
@Setter
@Builder
@JsonPropertyOrder({ "id", "nombre", "condominioId", "condominioNombre", "estado" })
public class TorreResponseDTO {

    private Long id;
    private String nombre;
    private EstadoTorre estado;
    private Long condominioId;
    private String condominioNombre;
}