package com.condosaas.api.module.inquilino_temporal.service;

import com.condosaas.api.module.inquilino_temporal.dto.*;

import java.util.List;

public interface InquilinoTemporalService {

    InquilinoTemporalResponseDTO create(InquilinoTemporalRequestDTO dto);

    InquilinoTemporalResponseDTO getById(Long id);

    List<InquilinoTemporalResponseDTO> getAll(Long estacionamientoId);

    InquilinoTemporalResponseDTO update(Long id, InquilinoTemporalRequestDTO dto);

    void delete(Long id);
}
