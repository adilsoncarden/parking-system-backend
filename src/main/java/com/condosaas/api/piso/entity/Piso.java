package com.condosaas.api.piso.entity;

import com.condosaas.api.torres.entity.Torres;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "piso")
public class Piso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_piso", nullable = false)
    private Integer numeroPiso;

    @ManyToOne
    @JoinColumn(name = "id_torres", nullable = false)
    private Torres torre;
}