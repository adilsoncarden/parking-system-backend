package com.condosaas.api.module.log_acceso_vehicular.model;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pase_invitado")
    private PaseInvitado paseInvitado;
}