package com.condosaas.api.module.configuracion_multa.service;

import com.condosaas.api.module.configuracion_multa.dto.*;

import java.math.BigDecimal;
import java.util.List;

public interface ConfiguracionMultaService {

    List<ConfiguracionMultaResponseDTO> getAll();

    ConfiguracionMultaResponseDTO getByCondominio(Long condominioId);

    ConfiguracionMultaResponseDTO upsert(ConfiguracionMultaRequestDTO dto);

    void delete(Long condominioId);

    // Usados por el módulo de préstamos: valores efectivos del condominio
    // (los guardados, o los por defecto del sistema si no hay config).
    int limiteMinutos(Long condominioId);

    BigDecimal tarifaPorMinuto(Long condominioId);
}
