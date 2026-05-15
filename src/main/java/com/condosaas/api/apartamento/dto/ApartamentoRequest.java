package com.condosaas.api.apartamento.dto;

import lombok.Data;

@Data
public class ApartamentoRequest {
    private String numero;
    private Long idPiso;
    private String propietario;
    private String estado;
}