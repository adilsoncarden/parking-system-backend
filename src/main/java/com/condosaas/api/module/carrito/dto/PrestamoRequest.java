package com.condosaas.api.module.carrito.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoRequest {
    @NotNull
    private Long idCarrito;
    @NotNull
    private Long idApartamento;
    @NotNull
    private Long idEntradaSalida;
}
