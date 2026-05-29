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
public class Piso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_piso")
    private Integer numeroPiso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_torres")
    private Torre torre;
}
