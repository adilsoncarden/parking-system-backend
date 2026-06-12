package com.condosaas.api.module.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardStatsDTO {
    private final long totalCondominios;
    private final long totalTorres;
    private final long totalPisos;
    private final long totalApartamentos;
    private final long apartamentosDisponibles;
    private final long apartamentosOcupados;
    private final long apartamentosMantenimiento;
    private final long apartamentosInactivos;
}
