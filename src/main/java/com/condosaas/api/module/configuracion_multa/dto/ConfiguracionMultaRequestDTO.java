package com.condosaas.api.module.configuracion_multa.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
public class ConfiguracionMultaRequestDTO {

    @NotNull
    private Long condominioId;

    @NotNull
    @Min(0)
    private Integer tiempoLimiteMinutos;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal tarifaPorMinuto;
}
