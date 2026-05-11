package com.condosaas.api.torres.dto;


import lombok.Data;

@Data
public class TorreRequest {
    private String nombre;
    private Long idCondominio;
}
