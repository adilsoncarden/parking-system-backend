package com.condosaas.api.module.vehiculo.dto;

import com.condosaas.api.module.vehiculo.model.EstadoVehiculo;
import lombok.*;

@Getter
@Setter
@Builder
public class VehiculoResponseDTO {

    private Long id;
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private EstadoVehiculo estado;

    private Long usuarioId;
    private String usuarioNombre;

    // Datos del dueño (para el módulo Parking: dónde vive y qué tipo de ocupante es)
    private Long apartamentoId;
    private String unidad;        // número del apartamento (departamento) del dueño
    private String tipoOcupante;  // PROPIETARIO | INQUILINO | VISITANTE

    // Ubicación completa del dueño (para la ficha al escanear la placa)
    private Integer pisoNumero;
    private String torreNombre;
    private String condominioNombre;
}