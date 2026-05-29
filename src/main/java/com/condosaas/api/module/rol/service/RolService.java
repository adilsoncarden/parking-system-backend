package com.condosaas.api.module.rol.service;

import com.condosaas.api.module.rol.dto.*;

import java.util.List;

public interface RolService {

    RolResponseDTO create(RolRequestDTO dto);

    RolResponseDTO getById(Long id);

    List<RolResponseDTO> getAll();

    RolResponseDTO update(Long id, RolRequestDTO dto);

    void delete(Long id);
}