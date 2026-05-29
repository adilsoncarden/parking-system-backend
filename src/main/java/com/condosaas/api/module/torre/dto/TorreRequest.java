package com.condosaas.api.module.torre.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TorreRequest {
    @NotBlank
    private String nombre;
    @NotNull
    private Long idCondominio;
    private Integer cantidadPisos;
    private Integer cantidadApartamentos;
}
