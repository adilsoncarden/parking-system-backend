package com.condosaas.api.module.apartamento.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EstadoApartamentoConverter implements AttributeConverter<EstadoApartamento, String> {

    @Override
    public String convertToDatabaseColumn(EstadoApartamento attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public EstadoApartamento convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        return EstadoApartamento.fromValue(dbData);
    }
}
