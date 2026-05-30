package com.condosaas.api.module.detalle_plaza_parkcontrol.dto;

import com.condosaas.api.module.detalle_plaza_parkcontrol.model.*;
import lombok.*;

@Getter
@Setter
@Builder
public class DetallePlazaResponseDTO {

    private Long id;
    private TipoPlaza tipo;
    private String numeroPlaza;
    private String observaciones;
    private EstadoRegistro estadoRegistro;

    private Long estacionamientoId;
    private String codigoEstacionamiento;

    private Long zonaId;
}