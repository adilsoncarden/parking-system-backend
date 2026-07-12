package com.condosaas.api.module.configuracion_multa.model;

import com.condosaas.api.module.condominio.model.Condominio;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Configuración de multas por condominio (spec V6: el Admin_Condominio configura las
 * multas). Un condominio tiene una sola configuración; si no existe, el sistema usa los
 * valores por defecto de {@code carrito.penalizacion.*}.
 */
@Entity
@Table(name = "configuracion_multa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracionMulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_configuracion_multa")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio", nullable = false, unique = true)
    private Condominio condominio;

    @Column(name = "tiempo_limite_minutos", nullable = false)
    private Integer tiempoLimiteMinutos;

    @Column(name = "tarifa_por_minuto", nullable = false, precision = 12, scale = 2)
    private BigDecimal tarifaPorMinuto;
}
