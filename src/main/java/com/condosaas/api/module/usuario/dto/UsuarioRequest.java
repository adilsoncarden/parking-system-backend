package com.condosaas.api.module.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private Integer roleId;
}
