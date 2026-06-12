package com.condosaas.api.module.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class UsuarioAuthDTO {

    private Long id;
    private String email;
    private String nombres;
    private String apellidos;
    private Long rolId;
    private String rolNombre;
    private Long condominioId;
    private String condominioNombre;
}
