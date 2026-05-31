package com.condosaas.api.module.pase_invitado.dto;

import com.condosaas.api.module.pase_invitado.model.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaseInvitadoRequestDTO {

    @NotBlank
    private String codigo;

    @NotBlank
    private String nombreInvitado;

    @NotNull
    private LocalDateTime fechaInicio;

    @NotNull
    private LocalDateTime fechaFin;

    @NotNull
    private EstadoPase estado;

    @NotNull
    private MetodoGeneracion metodo;

    @NotNull
    private Long usuarioId;

    private Long vehiculoId;
}