package com.condosaas.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConfigurationProperties(prefix = "carrito.penalizacion")
@Getter
@Setter
public class CarritoPenalizacionProperties {

    private int tiempoLimiteMinutos = 15;
    private BigDecimal tarifaPorMinuto = new BigDecimal("1.00");
}
