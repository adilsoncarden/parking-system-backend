package com.condosaas.api.module.torre.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TorreResponse {
    private Long idTorres;
    private String nombre;
    private Integer cantidadPisos;
    private Integer cantidadApartamentos;
    private LocalDateTime createdAt;
    private Long idCondominio;
}
