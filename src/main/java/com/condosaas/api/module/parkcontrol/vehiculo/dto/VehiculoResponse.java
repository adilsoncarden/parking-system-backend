package com.condosaas.api.module.parkcontrol.vehiculo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoResponse {
    private Long id;
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private Long idCondominio;
    private Long idApartamento;
}
