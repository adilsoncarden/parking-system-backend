package com.condosaas.api.module.carrito_carga.dto;

import com.condosaas.api.module.carrito_carga.model.EstadoCarrito;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class CarritoCargaRequestDTO {

    @NotBlank
    private String codigo;

    private String descripcion;

    @NotNull
    private EstadoCarrito estado;

    @NotNull
    private Long condominioId;
}