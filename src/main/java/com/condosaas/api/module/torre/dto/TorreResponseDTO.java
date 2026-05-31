package com.condosaas.api.module.torre.dto;

import com.condosaas.api.module.torre.model.EstadoTorre;
import lombok.*;

@Getter
@Setter
@Builder
public class TorreResponseDTO {

    private Long id;
    private String nombre;
    private EstadoTorre estado;
    private Long condominioId;
    private String condominioNombre;
}