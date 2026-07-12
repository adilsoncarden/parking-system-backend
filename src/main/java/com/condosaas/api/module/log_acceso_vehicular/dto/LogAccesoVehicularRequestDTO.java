package com.condosaas.api.module.log_acceso_vehicular.dto;

import com.condosaas.api.module.log_acceso_vehicular.model.*;
import com.condosaas.api.module.usuario.model.TipoOcupante;
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

    // Propietario / Inquilino (spec V6). Opcional.
    private TipoOcupante tipoOcupante;

    // Datos adicionales del inquilino (spec V6). Opcional.
    private String datosInquilino;

    @NotNull
    private Long vehiculoId;

    // Plaza física ocupada (spec V6). Opcional.
    private Long estacionamientoId;

    private Long paseInvitadoId;
}