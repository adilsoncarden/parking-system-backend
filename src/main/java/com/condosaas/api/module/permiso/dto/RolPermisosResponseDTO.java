package com.condosaas.api.module.permiso.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class RolPermisosResponseDTO {

    private Long rolId;
    private String rolNombre;
    private List<Long> permisoIds;
    private List<String> permisoNombres;
}
