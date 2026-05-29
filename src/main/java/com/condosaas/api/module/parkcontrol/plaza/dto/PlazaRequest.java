package com.condosaas.api.module.parkcontrol.plaza.dto;

import com.condosaas.api.module.enums.EstadoOcupacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlazaRequest {
    @NotBlank
    private String codigo;
    private EstadoOcupacion estado;
    @NotNull
    private Long idZona;
}
