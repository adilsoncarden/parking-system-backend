package com.condosaas.api.module.parkcontrol.permanencia.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermanenciaResponse {
    private Long id;
    private LocalDateTime calculadoAt;
    private Long minutos;
    private Double monto;
    private Long idAcceso;
}
