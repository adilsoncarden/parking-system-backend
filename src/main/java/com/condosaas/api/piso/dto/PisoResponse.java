package com.condosaas.api.piso.dto;

import lombok.Data;

@Data
public class PisoResponse {

    private Long id;
    private Integer numeroPiso;
    private Long idTorre;
    private String nombreTorre;
}