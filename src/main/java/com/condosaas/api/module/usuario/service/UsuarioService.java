package com.condosaas.api.module.usuario.service;

import com.condosaas.api.module.usuario.dto.UsuarioRequest;
import com.condosaas.api.module.usuario.dto.UsuarioResponse;
import java.util.List;

public interface UsuarioService {
    List<UsuarioResponse> findAll();

    UsuarioResponse findById(Long id);

    UsuarioResponse create(UsuarioRequest request);

    UsuarioResponse update(Long id, UsuarioRequest request);

    void delete(Long id);
}
