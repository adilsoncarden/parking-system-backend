package com.condosaas.api.entrada.dto;

import lombok.Data;

@Data
public class EntradaSalidaResponse {

    private Long id;
    private String nombre;
    private Long idCondominio;
    private String nombreCondominio;
}