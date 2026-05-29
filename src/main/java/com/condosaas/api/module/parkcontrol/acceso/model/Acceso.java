package com.condosaas.api.module.parkcontrol.acceso.model;

import com.condosaas.api.module.parkcontrol.pase_invitado.model.PaseInvitado;
import com.condosaas.api.module.parkcontrol.plaza.model.Plaza;
import com.condosaas.api.module.parkcontrol.vehiculo.model.Vehiculo;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "acceso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Acceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hora_entrada")
    private LocalDateTime horaEntrada;

    @Column(name = "hora_salida")
    private LocalDateTime horaSalida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pase_invitado")
    private PaseInvitado paseInvitado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plaza")
    private Plaza plaza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;
}
