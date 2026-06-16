package com.condosaas.api.module.dashboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DashboardParkingDTO {

    private long totalPlazas;
    private long plazasLibres;
    private long plazasOcupadas;
    private long plazasReservadas;
    private long plazasInactivas;

    private long totalVehiculos;
    private long permanenciasActivas;
    private long accesosHoy;
}
