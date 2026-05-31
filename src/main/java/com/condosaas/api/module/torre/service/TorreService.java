package com.condosaas.api.module.torre.service;

import com.condosaas.api.module.torre.dto.*;

import java.util.List;

public interface TorreService {

    TorreResponseDTO create(TorreRequestDTO dto);

    TorreResponseDTO getById(Long id);

    List<TorreResponseDTO> getAll(Long condominioId);

    TorreResponseDTO update(Long id, TorreRequestDTO dto);

    void delete(Long id);
}