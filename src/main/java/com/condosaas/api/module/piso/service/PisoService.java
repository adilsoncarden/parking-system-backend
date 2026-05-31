package com.condosaas.api.module.piso.service;

import com.condosaas.api.module.piso.dto.*;

import java.util.List;

public interface PisoService {

    PisoResponseDTO create(PisoRequestDTO dto);

    PisoResponseDTO getById(Long id);

    List<PisoResponseDTO> getAll(Long torreId);

    PisoResponseDTO update(Long id, PisoRequestDTO dto);

    void delete(Long id);
}