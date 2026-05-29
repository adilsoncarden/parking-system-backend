package com.condosaas.api.module.carrito.dto;

import com.condosaas.api.module.enums.EstadoPrestamo;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoResponse {
    private Long id;
    private EstadoPrestamo estado;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private Double montoMulta;
    private Boolean multado;
    private Long idApartamento;
    private Long idCarrito;
    private Long idEntradaSalida;
}
