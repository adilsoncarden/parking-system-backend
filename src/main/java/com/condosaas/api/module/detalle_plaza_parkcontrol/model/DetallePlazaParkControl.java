package com.condosaas.api.module.detalle_plaza_parkcontrol.model;

import com.condosaas.api.module.estacionamiento.model.Estacionamiento;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_plaza_parkcontrol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePlazaParkControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_plaza")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoPlaza tipo;

    @Column(name = "numero_plaza", nullable = false)
    private String numeroPlaza;

    @Column(name = "observaciones")
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_registro", nullable = false)
    private EstadoRegistro estadoRegistro;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estacionamiento", nullable = false, unique = true)
    private Estacionamiento estacionamiento;
}