package com.condosaas.api.prestamo.dto;

import lombok.Data;

@Data
public class PrestamoRequest {

    private Long idCarrito;
    private Long idApartamento;
    private Long idEntradaSalida;
}