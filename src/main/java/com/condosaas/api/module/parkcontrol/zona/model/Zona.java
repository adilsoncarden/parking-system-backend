package com.condosaas.api.module.parkcontrol.zona.model;

import com.condosaas.api.module.condominio.model.Condominio;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "zona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Zona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio")
    private Condominio condominio;
}
