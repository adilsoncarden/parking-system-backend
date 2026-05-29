package com.condosaas.api.module.parkcontrol.acceso.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccesoResponse {
    private Long id;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private Long idVehiculo;
    private Long idPlaza;
    private Long idPaseInvitado;
}
