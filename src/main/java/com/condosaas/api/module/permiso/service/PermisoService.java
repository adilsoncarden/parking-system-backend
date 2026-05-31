package com.condosaas.api.module.permiso.service;

import com.condosaas.api.module.permiso.dto.*;

import java.util.List;

public interface PermisoService {

    PermisoResponseDTO create(PermisoRequestDTO dto);

    PermisoResponseDTO getById(Long id);

    List<PermisoResponseDTO> getAll();

    PermisoResponseDTO update(Long id, PermisoRequestDTO dto);

    void delete(Long id);

    RolPermisosResponseDTO getPermisosByRol(Long rolId);

    RolPermisosResponseDTO assignPermisosToRol(Long rolId, RolPermisosAssignDTO dto);
}
