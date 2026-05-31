package com.condosaas.api.module.pase_invitado.dto;

import com.condosaas.api.module.pase_invitado.model.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PaseInvitadoResponseDTO {

    private Long id;
    private String codigo;
    private String nombreInvitado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    private EstadoPase estado;
    private MetodoGeneracion metodo;

    private Long usuarioId;
    private String usuarioNombre;

    private Long vehiculoId;
}