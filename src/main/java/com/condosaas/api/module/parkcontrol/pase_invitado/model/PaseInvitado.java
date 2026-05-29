package com.condosaas.api.module.parkcontrol.pase_invitado.model;

import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.condominio.model.Condominio;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pase_invitado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaseInvitado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "nombre_visitante", length = 100)
    private String nombreVisitante;

    @Column(name = "placa_visitante", length = 15)
    private String placaVisitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apartamento")
    private Apartamento apartamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio", nullable = false)
    private Condominio condominio;
}
