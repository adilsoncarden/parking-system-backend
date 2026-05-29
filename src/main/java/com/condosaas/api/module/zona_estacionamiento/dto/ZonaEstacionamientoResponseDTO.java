package com.condosaas.api.module.zona_estacionamiento.dto;

import com.condosaas.api.module.zona_estacionamiento.model.EstadoZonaEstacionamiento;
import lombok.*;

@Getter
@Setter
@Builder
public class ZonaEstacionamientoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private EstadoZonaEstacionamiento estado;

    private Long condominioId;
    private String condominioNombre;
}