package com.condosaas.api.module.usuario.service;

import com.condosaas.api.module.usuario.dto.*;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO create(UsuarioRequestDTO dto);

    UsuarioResponseDTO getById(Long id);

    List<UsuarioResponseDTO> getAll(Long rolId, Long apartamentoId, Long condominioId);

    UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto);

    void delete(Long id);
}