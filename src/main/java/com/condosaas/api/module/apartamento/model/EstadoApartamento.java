package com.condosaas.api.module.apartamento.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum EstadoApartamento {
    DISPONIBLE,
    OCUPADO,
    MANTENIMIENTO,
    INACTIVO;

    @JsonCreator
    public static EstadoApartamento fromValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Estado de apartamento no puede ser nulo o vacío");
        }
        String normalized = value.trim().toUpperCase(Locale.ROOT);
        for (EstadoApartamento estado : values()) {
            if (estado.name().equals(normalized)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado de apartamento desconocido: " + value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
