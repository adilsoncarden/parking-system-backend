package com.condosaas.api.module.zona_estacionamiento.service;

import com.condosaas.api.module.zona_estacionamiento.dto.*;

import java.util.List;

public interface ZonaEstacionamientoService {

    ZonaEstacionamientoResponseDTO create(ZonaEstacionamientoRequestDTO dto);

    ZonaEstacionamientoResponseDTO getById(Long id);

    List<ZonaEstacionamientoResponseDTO> getAll(Long condominioId);

    ZonaEstacionamientoResponseDTO update(Long id, ZonaEstacionamientoRequestDTO dto);

    void delete(Long id);
}