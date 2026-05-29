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

    @Column(name = "calculado_at")
    private LocalDateTime calculadoAt;

    private Long minutos;
    private Double monto;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_acceso", unique = true)
    private Acceso acceso;
}
