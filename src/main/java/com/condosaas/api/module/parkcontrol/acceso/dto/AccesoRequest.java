package com.condosaas.api.module.parkcontrol.acceso.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccesoRequest {
    @NotNull
    private Long idVehiculo;
    private Long idPlaza;
    private Long idPaseInvitado;
}
