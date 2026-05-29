package com.condosaas.api.module.condominio.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CondominioResponse {
    private Long id;
    private String nombre;
    private String direccion;
    private String tipo;
    private String imagen;
    private Double latitud;
    private Double longitud;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
