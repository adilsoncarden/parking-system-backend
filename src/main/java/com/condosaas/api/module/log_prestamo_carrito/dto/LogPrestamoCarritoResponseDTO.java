package com.condosaas.api.module.log_prestamo_carrito.dto;

import com.condosaas.api.module.log_prestamo_carrito.model.EstadoPrestamo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LogPrestamoCarritoResponseDTO {

    private Long id;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucion;
    private EstadoPrestamo estado;

    private Long carritoId;
    private String codigoCarrito;

    private Long usuarioId;
    private String usuarioNombre;
}