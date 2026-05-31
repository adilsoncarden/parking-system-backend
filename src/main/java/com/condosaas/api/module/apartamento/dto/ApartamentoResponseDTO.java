package com.condosaas.api.module.apartamento.dto;

import com.condosaas.api.module.apartamento.model.EstadoApartamento;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

@Getter
@Setter
@Builder
@JsonPropertyOrder({ "id", "numero", "area", "condominioId", "torreId", "torreNombre", "pisoId", "pisoNumero",
        "estado" })
public class ApartamentoResponseDTO {

    private Long id;
    private String numero;
    private Double area;
    private EstadoApartamento estado;

    private Long pisoId;
    private Integer pisoNumero;

    private Long torreId;
    private String torreNombre;

    private Long condominioId;
}