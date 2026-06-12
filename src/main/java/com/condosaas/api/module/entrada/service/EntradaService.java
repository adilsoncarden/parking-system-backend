package com.condosaas.api.module.entrada.service;

import com.condosaas.api.module.entrada.dto.*;

import java.util.List;

public interface EntradaService {

    EntradaResponseDTO create(EntradaRequestDTO dto);

    EntradaResponseDTO getById(Long id);

    List<EntradaResponseDTO> getAll(Long condominioId);

    EntradaResponseDTO update(Long id, EntradaRequestDTO dto);

    void delete(Long id);
}
