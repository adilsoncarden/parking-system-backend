package com.condosaas.api.condominio.dto;

import lombok.Data;

@Data
public class CondominioRequest {
    private String nombre;
    private String direccion;
    private String tipo;
    private String imagen;
    private Double latitud;
    private Double longitud;
}