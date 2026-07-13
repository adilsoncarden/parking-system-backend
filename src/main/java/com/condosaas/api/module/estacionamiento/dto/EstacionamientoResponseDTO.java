package com.condosaas.api.module.estacionamiento.dto;

import com.condosaas.api.module.estacionamiento.model.EstadoOcupacion;
import com.condosaas.api.module.estacionamiento.model.TipoVehiculo;
import lombok.*;

@Getter
@Setter
@Builder
public class EstacionamientoResponseDTO {

    private Long id;
    private String codigo;
    private EstadoOcupacion estadoOcupacion;

    private TipoVehiculo tipoVehiculo;
    private Integer capacidad;
    private Integer ocupacionActual;

    private Long zonaEstacionamientoId;
    private String zonaNombre;

    // Apartamento propietario de la plaza (spec V6). Null si no está asignado.
    private Long apartamentoId;
    private String apartamentoNumero;

    private Long condominioId;
    private String condominioNombre;

    // Ocupación actual (null si la plaza está libre)
    private Long vehiculoActualId;
    private String placaActual;
}