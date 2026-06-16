package com.condosaas.api.module.estacionamiento.model;

import com.condosaas.api.module.vehiculo.model.Vehiculo;
import com.condosaas.api.module.zona_estacionamiento.model.ZonaEstacionamiento;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estacionamiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estacionamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estacionamiento")
    private Long id;

    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_ocupacion", nullable = false)
    private EstadoOcupacion estadoOcupacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zona_estacionamiento", nullable = false)
    private ZonaEstacionamiento zona;

    // Vehículo que ocupa actualmente la plaza (null = libre). Lo gestiona el flujo de entrada/salida.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo_actual")
    private Vehiculo vehiculoActual;
}