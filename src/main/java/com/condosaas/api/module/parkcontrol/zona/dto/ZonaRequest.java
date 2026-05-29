package com.condosaas.api.module.parkcontrol.zona.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZonaRequest {
    @NotBlank
    private String nombre;
    private String descripcion;
    @NotNull
    private Long idCondominio;
}
