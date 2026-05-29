package com.condosaas.api.carrito.dto;

import lombok.Data;

@Data
public class CarritoCargaResponse {

    private Long id;
    private String nombre;
    private Boolean disponible;
    private Long idEntradaSalida;
    private String nombreEntrada;
    private Long idCondominio;
    private String nombreCondominio;
}