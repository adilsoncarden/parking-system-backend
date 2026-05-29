package com.condosaas.api.module.acceso.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntradaSalidaRequest {
    @NotBlank
    private String nombre;
    @NotNull
    private Long idCondominio;
}
