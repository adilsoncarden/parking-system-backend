package com.condosaas.api.module.torre.model;

import com.condosaas.api.module.condominio.model.Condominio;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "torre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Torre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_torre")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio", nullable = false)
    private Condominio condominio;
}