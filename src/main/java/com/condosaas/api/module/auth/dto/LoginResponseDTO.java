package com.condosaas.api.module.auth.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class LoginResponseDTO {

    private String token;
    private String tipo;
    private UsuarioAuthDTO usuario;
    private List<String> permisos;
}
