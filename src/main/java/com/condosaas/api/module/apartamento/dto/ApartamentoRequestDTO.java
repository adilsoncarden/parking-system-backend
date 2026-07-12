package com.condosaas.api.module.apartamento.dto;

import com.condosaas.api.module.apartamento.model.EstadoApartamento;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class ApartamentoRequestDTO {

    @NotBlank
    private String numero;

    private Double area;

    // Derecho a estacionamiento de la unidad (spec V6). Si no viene, se asume false.
    private Boolean derechoEstacionamiento;

    @NotNull
    private EstadoApartamento estado;

    @NotNull
    private Long pisoId;
}