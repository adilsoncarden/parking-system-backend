package com.condosaas.api.module.estacionamiento.dto;

import com.condosaas.api.module.enums.EstadoOcupacion;
import com.condosaas.api.module.enums.TipoEstacionamiento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstacionamientoRequest {
    @NotBlank
    private String codigo;
    private EstadoOcupacion estado;
    private TipoEstacionamiento tipo;
    private Long idApartamento;
    @NotNull
    private Long idCondominio;
}
