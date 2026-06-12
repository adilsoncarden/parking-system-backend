package com.condosaas.api.module.usuario.dto;

import com.condosaas.api.module.usuario.model.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class UsuarioRequestDTO {

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    @Email
    @NotBlank
    private String email;

    private String telefono;

    @NotBlank
    private String password;

    @NotNull
    private TipoOcupante tipoOcupante;

    @NotNull
    private EstadoUsuario estado;

    @NotNull
    private Long rolId;

    private Long apartamentoId;

    // Condominio asignado (para admins de condominio). Opcional.
    private Long condominioId;
}