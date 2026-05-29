package com.condosaas.api.module.usuario.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String username;
    private Integer roleId;
    private String roleName;
}
