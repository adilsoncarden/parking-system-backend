package com.condosaas.api.module.log_acceso_vehicular.dto;

import com.condosaas.api.module.log_acceso_vehicular.model.*;
import com.condosaas.api.module.usuario.model.TipoOcupante;
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

    private TipoOcupante tipoOcupante;
    private String datosInquilino;

    private Long vehiculoId;
    private String placa;

    // Plaza física ocupada (spec V6). Null si no aplica.
    private Long estacionamientoId;
    private String estacionamientoCodigo;

    private Long paseInvitadoId;
}