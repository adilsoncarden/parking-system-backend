package com.condosaas.api.module.detalle_plaza_parkcontrol.service;

import com.condosaas.api.module.detalle_plaza_parkcontrol.dto.*;

import java.util.List;

public interface DetallePlazaService {

    DetallePlazaResponseDTO create(DetallePlazaRequestDTO dto);

    DetallePlazaResponseDTO getById(Long id);

    List<DetallePlazaResponseDTO> getAll(Long estacionamientoId);

    DetallePlazaResponseDTO update(Long id, DetallePlazaRequestDTO dto);

    void delete(Long id);
}