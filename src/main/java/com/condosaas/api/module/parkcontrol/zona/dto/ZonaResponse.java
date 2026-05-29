package com.condosaas.api.module.parkcontrol.zona.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZonaResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long idCondominio;
}
