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

    private Boolean activo;

    @Column(unique = true)
    private String codigo;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "nombre_visitante")
    private String nombreVisitante;

    @Column(name = "placa_visitante")
    private String placaVisitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apartamento")
    private Apartamento apartamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio")
    private Condominio condominio;
}
