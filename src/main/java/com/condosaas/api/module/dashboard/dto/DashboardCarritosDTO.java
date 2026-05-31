package com.condosaas.api.module.dashboard.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCarritosDTO {

    private EstadisticasCarrito estadisticas;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EstadisticasCarrito {
        private long totalCarritos;
        private long prestados;
        private long penalizados;
        private long disponibles;
        private long prestamosActivos;
        private BigDecimal totalRecaudado;
    }
}
