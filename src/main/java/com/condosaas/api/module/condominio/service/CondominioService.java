package com.condosaas.api.module.condominio.service;

import com.condosaas.api.module.condominio.dto.*;

import java.util.List;

public interface CondominioService {

    CondominioResponseDTO create(CondominioRequestDTO dto);

    CondominioResponseDTO getById(Long id);

    List<CondominioResponseDTO> getAll(Long estado);

    CondominioResponseDTO update(Long id, CondominioRequestDTO dto);

    void delete(Long id);
}