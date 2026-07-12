package com.condosaas.api.module.inquilino_temporal.model;

import com.condosaas.api.module.estacionamiento.model.Estacionamiento;
import com.condosaas.api.module.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Inquilino temporal (spec V6): persona autorizada temporalmente por el dueño para usar
 * una plaza de estacionamiento. Un estacionamiento puede tener varios inquilinos a lo
 * largo del tiempo (Estacionamiento 1:N InquilinoTemporal).
 */
@Entity
@Table(name = "inquilino_temporal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquilinoTemporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inquilino_temporal")
    private Long id;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    // Documento de identidad (DNI) del inquilino.
    @Column(name = "documento", nullable = false)
    private String documento;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoInquilino estado;

    // Plaza para la que queda autorizado.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estacionamiento", nullable = false)
    private Estacionamiento estacionamiento;

    // Propietario que lo registra.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_propietario", nullable = false)
    private Usuario propietario;
}
