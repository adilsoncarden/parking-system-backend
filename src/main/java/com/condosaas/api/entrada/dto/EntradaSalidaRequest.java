package com.condosaas.api.entrada.dto;

import lombok.Data;

@Data
public class EntradaSalidaRequest {

    private String nombre;
    private Long idCondominio;
}