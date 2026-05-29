package com.condosaas.api.module.carrito.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarritoRequest {
    @NotBlank
    private String nombre;
    @NotNull
    private Long idEntradaSalida;
}
