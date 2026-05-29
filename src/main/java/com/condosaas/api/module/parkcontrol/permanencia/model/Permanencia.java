package com.condosaas.api.module.parkcontrol.permanencia.model;

import com.condosaas.api.module.parkcontrol.acceso.model.Acceso;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "permanencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permanencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "calculado_at", nullable = false)
    private LocalDateTime calculadoAt;

    @Column(nullable = false)
    private Long minutos;

    @Column(nullable = false)
    private Double monto;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_acceso", nullable = false, unique = true)
    private Acceso acceso;
}
