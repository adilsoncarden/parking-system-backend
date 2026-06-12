package com.condosaas.api.module.entrada.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Getter
@Setter
@Builder
@JsonPropertyOrder({ "id", "nombre", "capacidadCarritos", "condominioId", "condominioNombre" })
public class EntradaResponseDTO {

    private Long id;
    private String nombre;
    private Integer capacidadCarritos;
    private Long condominioId;
    private String condominioNombre;
}
