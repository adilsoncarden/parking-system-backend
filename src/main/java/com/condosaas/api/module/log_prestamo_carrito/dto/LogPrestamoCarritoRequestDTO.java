package com.condosaas.api.module.log_prestamo_carrito.dto;

import com.condosaas.api.module.log_prestamo_carrito.model.EstadoPrestamo;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class LogPrestamoCarritoRequestDTO {

    @NotNull
    private LocalDateTime fechaPrestamo;

    private LocalDateTime fechaDevolucion;

    @NotNull
    private EstadoPrestamo estado;

    @NotNull
    private Long carritoId;

    @NotNull
    private Long usuarioId;
}