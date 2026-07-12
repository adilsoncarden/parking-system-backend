package com.condosaas.api.module.configuracion_multa.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ConfiguracionMultaResponseDTO {

    private Long id;
    private Long condominioId;
    private String condominioNombre;
    private Integer tiempoLimiteMinutos;
    private BigDecimal tarifaPorMinuto;

    // true = no hay config guardada, se devuelven los valores por defecto del sistema.
    private boolean porDefecto;
}
