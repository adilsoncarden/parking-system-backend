package com.condosaas.api.module.log_prestamo_carrito.dto;

import com.condosaas.api.module.log_prestamo_carrito.model.EstadoPrestamo;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class LogPrestamoCarritoRequestDTO {

    private LocalDateTime fechaPrestamo;

    private LocalDateTime fechaDevolucion;

    private EstadoPrestamo estado;

    @NotNull
    private Long carritoId;

    @NotNull
    private Long usuarioId;
}
