package com.condosaas.api.module.detalle_plaza_parkcontrol.dto;

import com.condosaas.api.module.detalle_plaza_parkcontrol.model.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class DetallePlazaRequestDTO {

    @NotNull
    private TipoPlaza tipo;

    @NotBlank
    private String numeroPlaza;

    private String observaciones;

    @NotNull
    private EstadoRegistro estadoRegistro;

    @NotNull
    private Long estacionamientoId;
}