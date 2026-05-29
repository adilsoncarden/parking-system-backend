package com.condosaas.api.module.condominio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CondominioRequest {
    @NotBlank
    private String nombre;
    private String direccion;
    private String tipo;
    private String imagen;
    private Double latitud;
    private Double longitud;
}
