package com.condosaas.api.module.log_acceso_vehicular.dto;

import com.condosaas.api.module.log_acceso_vehicular.model.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class LogAccesoVehicularRequestDTO {

    @NotNull
    private TipoAcceso tipo;

    @NotNull
    private MetodoAcceso metodo;

    @NotNull
    private LocalDateTime fechaHora;

    private String observacion;

    @NotNull
    private Long vehiculoId;

    private Long paseInvitadoId;
}