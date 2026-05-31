package com.condosaas.api.module.usuario.dto;

import com.condosaas.api.module.usuario.model.*;
import lombok.*;

@Getter
@Setter
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;

    private TipoOcupante tipoOcupante;
    private EstadoUsuario estado;

    private Long rolId;
    private String rolNombre;

    private Long apartamentoId;
}