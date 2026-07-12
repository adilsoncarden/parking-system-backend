package com.condosaas.api.module.log_acceso_vehicular.model;

import com.condosaas.api.module.estacionamiento.model.Estacionamiento;
import com.condosaas.api.module.usuario.model.TipoOcupante;
import com.condosaas.api.module.vehiculo.model.Vehiculo;
import com.condosaas.api.module.pase_invitado.model.PaseInvitado;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "log_acceso_vehicular")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogAccesoVehicular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log_acceso")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoAcceso tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo", nullable = false)
    private MetodoAcceso metodo;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "observacion")
    private String observacion;

    // Indicador de si el conductor es Propietario o Inquilino (spec V6). Nullable.
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ocupante")
    private TipoOcupante tipoOcupante;

    // Datos adicionales del inquilino/ocupante temporal (spec V6). Nullable.
    @Column(name = "datos_inquilino")
    private String datosInquilino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    // Plaza física ocupada en este acceso (spec V6). Nullable.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estacionamiento")
    private Estacionamiento estacionamiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pase_invitado")
    private PaseInvitado paseInvitado;
}