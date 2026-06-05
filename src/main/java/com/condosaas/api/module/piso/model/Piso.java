package com.condosaas.api.module.piso.model;

import com.condosaas.api.module.torre.model.Torre;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "piso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Piso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piso")
    private Long id;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_torre", nullable = false)
    private Torre torre;
}