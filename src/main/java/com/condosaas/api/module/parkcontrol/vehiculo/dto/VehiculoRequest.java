package com.condosaas.api.module.parkcontrol.vehiculo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoRequest {
    @NotBlank
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    @NotNull
    private Long idCondominio;
    private Long idApartamento;
}
