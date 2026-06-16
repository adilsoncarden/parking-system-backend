package com.condosaas.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuración a nivel de condominio.
 * Propiedad: app.condominio.max-entradas (por defecto 2).
 * El profesor pidió que la cantidad de entradas (puertas) no esté hardcodeada,
 * sino que sea una variable global configurable.
 */
@Component
@ConfigurationProperties(prefix = "app.condominio")
@Getter
@Setter
public class CondominioProperties {

    // Máximo de entradas (puertas) por condominio.
    private long maxEntradas = 2;
}
