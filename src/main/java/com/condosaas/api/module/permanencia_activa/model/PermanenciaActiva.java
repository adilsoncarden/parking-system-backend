package com.condosaas.api.module.permanencia_activa.model;

import com.condosaas.api.module.estacionamiento.model.Estacionamiento;
import com.condosaas.api.module.vehiculo.model.Vehiculo;
import com.condosaas.api.module.log_acceso_vehicular.model.LogAccesoVehicular;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "permanencia_activa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermanenciaActiva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permanencia")
    private Long id;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDateTime fechaEntrada;

    @Column(name = "fecha_salida")
    private LocalDateTime fechaSalida;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPermanencia estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    // Plaza que ocupa esta permanencia (para liberar el cupo correcto al salir,
    // incluso en plazas de motos con varios ocupantes). Nullable por compatibilidad.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estacionamiento")
    private Estacionamiento estacionamiento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_log_entrada", nullable = false)
    private LogAccesoVehicular logEntrada;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_log_salida")
    private LogAccesoVehicular logSalida;
}