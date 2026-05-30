package com.condosaas.api.module.estacionamiento.service;

import com.condosaas.api.module.estacionamiento.dto.*;

import java.util.List;

public interface EstacionamientoService {

    EstacionamientoResponseDTO create(EstacionamientoRequestDTO dto);

    EstacionamientoResponseDTO getById(Long id);

    List<EstacionamientoResponseDTO> getAll(Long zonaId);

    EstacionamientoResponseDTO update(Long id, EstacionamientoRequestDTO dto);

    void delete(Long id);
}