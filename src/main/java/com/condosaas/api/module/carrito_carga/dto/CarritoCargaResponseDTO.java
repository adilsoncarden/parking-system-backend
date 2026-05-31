package com.condosaas.api.module.carrito_carga.dto;

import com.condosaas.api.module.carrito_carga.model.EstadoCarrito;
import lombok.*;

@Getter
@Setter
@Builder
public class CarritoCargaResponseDTO {

    private Long id;
    private String codigo;
    private String descripcion;
    private EstadoCarrito estado;

    private Long condominioId;
    private String condominioNombre;
}