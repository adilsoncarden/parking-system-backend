package com.condosaas.api.module.apartamento.model;

import com.condosaas.api.module.piso.model.Piso;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "apartamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Apartamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_apartamento")
    private Long id;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "area")
    private Double area;

    @Column(name = "estado", nullable = false)
    private EstadoApartamento estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_piso", nullable = false)
    private Piso piso;
}