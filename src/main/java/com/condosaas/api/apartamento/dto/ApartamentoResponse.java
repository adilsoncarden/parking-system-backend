package com.condosaas.api.apartamento.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApartamentoResponse {
    private Long id;
    private String numero;
    private Long idPiso;
    private Integer numeroPiso;
    private String nombreTorre;
    private String propietario;
    private String estado;
    private LocalDateTime createdAt;
}