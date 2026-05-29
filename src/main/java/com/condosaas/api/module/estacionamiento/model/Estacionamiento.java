package com.condosaas.api.module.estacionamiento.model;

import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.enums.EstadoOcupacion;
import com.condosaas.api.module.enums.TipoEstacionamiento;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estacionamiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estacionamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @Enumerated(EnumType.STRING)
    private EstadoOcupacion estado;

    @Enumerated(EnumType.STRING)
    private TipoEstacionamiento tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apartamento")
    private Apartamento apartamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio")
    private Condominio condominio;
}
