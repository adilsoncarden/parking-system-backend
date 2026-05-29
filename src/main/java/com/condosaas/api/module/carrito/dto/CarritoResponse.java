package com.condosaas.api.module.carrito.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarritoResponse {
    private Long id;
    private Boolean disponible;
    private String nombre;
    private Long idEntradaSalida;
}
