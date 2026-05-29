package com.condosaas.api.module.estacionamiento.dto;

import com.condosaas.api.module.enums.EstadoOcupacion;
import com.condosaas.api.module.enums.TipoEstacionamiento;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstacionamientoResponse {
    private Long id;
    private String codigo;
    private EstadoOcupacion estado;
    private TipoEstacionamiento tipo;
    private Long idApartamento;
    private Long idCondominio;
}
