package com.condosaas.api.module.permanencia_activa.service;

import com.condosaas.api.module.permanencia_activa.dto.*;

import java.util.List;

public interface PermanenciaActivaService {

    PermanenciaActivaResponseDTO create(PermanenciaActivaRequestDTO dto);

    PermanenciaActivaResponseDTO getById(Long id);

    List<PermanenciaActivaResponseDTO> getAll(Long vehiculoId);

    PermanenciaActivaResponseDTO update(Long id, PermanenciaActivaRequestDTO dto);

    void delete(Long id);

    // Flujos de negocio (transaccionales)
    PermanenciaActivaResponseDTO registrarEntrada(RegistrarEntradaRequestDTO dto);

    PermanenciaActivaResponseDTO registrarSalida(RegistrarSalidaRequestDTO dto);
}