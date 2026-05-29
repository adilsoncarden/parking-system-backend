package com.condosaas.api.module.acceso.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccesoVehicularRequest {
    @NotBlank
    private String placa;
    @NotNull
    private Long idCondominio;
    private Long idPuntoAcceso;
    private String observaciones;
}
