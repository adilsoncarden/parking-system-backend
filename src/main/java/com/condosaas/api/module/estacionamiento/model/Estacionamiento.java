package com.condosaas.api.module.estacionamiento.model;

import com.condosaas.api.module.apartamento.model.Apartamento;
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

    // Tipo de vehículo que admite la plaza (spec V6: "1 auto o 4 motos").
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vehiculo", nullable = false, columnDefinition = "varchar(20) default 'AUTO'")
    private TipoVehiculo tipoVehiculo = TipoVehiculo.AUTO;

    // Cupo de la plaza (1 auto = 4 motos, por defecto según el tipo).
    @Builder.Default
    @Column(name = "capacidad", nullable = false, columnDefinition = "integer default 1")
    private Integer capacidad = 1;

    // Cuántos vehículos ocupan la plaza ahora mismo (para plazas de motos, 0..capacidad).
    @Builder.Default
    @Column(name = "ocupacion_actual", nullable = false, columnDefinition = "integer default 0")
    private Integer ocupacionActual = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zona_estacionamiento", nullable = false)
    private ZonaEstacionamiento zona;

    // Apartamento propietario de la plaza (spec V6: Estacionamiento -> Apartamento).
    // Nullable: convive con el modelo por zonas que consume el módulo de parking.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apartamento")
    private Apartamento apartamento;

    // Vehículo que ocupa actualmente la plaza (null = libre). Lo gestiona el flujo de entrada/salida.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo_actual")
    private Vehiculo vehiculoActual;
}