package com.condosaas.api.module.apartamento.service;

import com.condosaas.api.module.apartamento.dto.*;

import java.util.List;

public interface ApartamentoService {

    ApartamentoResponseDTO create(ApartamentoRequestDTO dto);

    ApartamentoResponseDTO getById(Long id);

    List<ApartamentoResponseDTO> getAll(Long pisoId);

    ApartamentoResponseDTO update(Long id, ApartamentoRequestDTO dto);

    void delete(Long id);
}