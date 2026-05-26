package com.condosaas.api.prestamo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrestamoResponse {

    private Long id;
    private Long idCarrito;
    private String nombreCarrito;
    private Long idApartamento;
    private String numeroApartamento;
    private String propietario;
    private Long idEntradaSalida;
    private String nombreEntrada;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private String estado;
    private Boolean multado;
    private Double montoMulta;
}