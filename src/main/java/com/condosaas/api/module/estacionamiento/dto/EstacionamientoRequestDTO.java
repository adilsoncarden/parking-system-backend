package com.condosaas.api.module.estacionamiento.dto;

import com.condosaas.api.module.estacionamiento.model.EstadoOcupacion;
import com.condosaas.api.module.estacionamiento.model.TipoVehiculo;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class EstacionamientoRequestDTO {

    @NotBlank
    private String codigo;

    @NotNull
    private EstadoOcupacion estadoOcupacion;

    @NotNull
    private Long zonaEstacionamientoId;

    // Apartamento propietario de la plaza (spec V6). Opcional.
    private Long apartamentoId;

    // Tipo de vehículo (AUTO/MOTO). Si no viene, AUTO.
    private TipoVehiculo tipoVehiculo;

    // Cupo de la plaza. Si no viene, se deriva del tipo (AUTO=1, MOTO=4).
    private Integer capacidad;
}