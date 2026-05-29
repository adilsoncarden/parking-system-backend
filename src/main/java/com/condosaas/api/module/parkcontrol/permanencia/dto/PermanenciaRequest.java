package com.condosaas.api.module.parkcontrol.permanencia.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermanenciaRequest {
    @NotNull
    private Long idAcceso;
    @NotNull
    private Double tarifaPorHora;
}
