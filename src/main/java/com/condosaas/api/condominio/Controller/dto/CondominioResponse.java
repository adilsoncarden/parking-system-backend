package com.condosaas.api.condominio.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
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