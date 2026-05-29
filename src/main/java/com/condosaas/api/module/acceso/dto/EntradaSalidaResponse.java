package com.condosaas.api.module.acceso.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntradaSalidaResponse {
    private Long id;
    private String nombre;
    private Long idCondominio;
}
