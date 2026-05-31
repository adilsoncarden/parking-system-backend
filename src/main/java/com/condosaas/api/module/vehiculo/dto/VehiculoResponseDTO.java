package com.condosaas.api.module.vehiculo.dto;

import com.condosaas.api.module.vehiculo.model.EstadoVehiculo;
import lombok.*;

@Getter
@Setter
@Builder
public class VehiculoResponseDTO {

    private Long id;
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private EstadoVehiculo estado;

    private Long usuarioId;
    private String usuarioNombre;
}