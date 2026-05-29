package com.condosaas.api.carrito.dto;

import lombok.Data;

@Data
public class CarritoCargaRequest {

    private String nombre;
    private Long idEntradaSalida;
}