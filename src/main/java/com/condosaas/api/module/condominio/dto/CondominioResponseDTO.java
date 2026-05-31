package com.condosaas.api.module.condominio.dto;

import com.condosaas.api.module.condominio.model.EstadoCondominio;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

@Getter
@Setter
@Builder
@JsonPropertyOrder({ "id", "nombre", "email", "telefono", "direccion", "estado" })
public class CondominioResponseDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private EstadoCondominio estado;
}