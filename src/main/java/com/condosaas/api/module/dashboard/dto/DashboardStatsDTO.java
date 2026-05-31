package com.condosaas.api.module.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardStatsDTO {
    private long totalCondominios;
    private long totalTorres;
    private long totalPisos;
    private long totalApartamentos;
    private long apartamentosDisponibles;
    private long apartamentosOcupados;
    private long apartamentosMantenimiento;
    private long apartamentosInactivos;
}
