package com.condosaas.api.module.log_acceso_vehicular.dto;

import com.condosaas.api.module.log_acceso_vehicular.model.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LogAccesoVehicularResponseDTO {

    private Long id;
    private TipoAcceso tipo;
    private MetodoAcceso metodo;
    private LocalDateTime fechaHora;
    private String observacion;

    private Long vehiculoId;
    private String placa;

    private Long paseInvitadoId;
}