package com.condosaas.api.module.vehiculo.dto;

import com.condosaas.api.module.vehiculo.model.EstadoVehiculo;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class VehiculoRequestDTO {

    @NotBlank
    private String placa;

    private String marca;
    private String modelo;
    private String color;

    @NotNull
    private EstadoVehiculo estado;

    @NotNull
    private Long usuarioId;
}