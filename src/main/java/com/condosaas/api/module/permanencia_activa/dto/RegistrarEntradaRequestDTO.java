package com.condosaas.api.module.permanencia_activa.dto;

import com.condosaas.api.module.log_acceso_vehicular.model.MetodoAcceso;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarEntradaRequestDTO {

    @NotBlank
    private String placa;

    // Método de registro del acceso. Si viene null se asume MANUAL.
    private MetodoAcceso metodo;

    // Plaza donde se estaciona (opcional). Si viene, se marca como ocupada.
    private Long estacionamientoId;

    private String observacion;
}
