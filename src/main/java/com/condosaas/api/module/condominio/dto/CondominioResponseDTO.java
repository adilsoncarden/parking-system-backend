package com.condosaas.api.module.condominio.dto;

import com.condosaas.api.module.condominio.model.EstadoCondominio;
import lombok.*;

@Getter
@Setter
@Builder
public class CondominioResponseDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private EstadoCondominio estado;
}