package com.condosaas.api.module.parkcontrol.plaza.dto;

import com.condosaas.api.module.enums.EstadoOcupacion;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlazaResponse {
    private Long id;
    private String codigo;
    private EstadoOcupacion estado;
    private Long idZona;
}
