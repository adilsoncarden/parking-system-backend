package com.condosaas.api.module.rol.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class RolResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
}