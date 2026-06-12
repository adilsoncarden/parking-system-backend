package com.condosaas.api.module.entrada.model;

import com.condosaas.api.module.condominio.model.Condominio;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "entrada")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrada")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    // "Puesto de carrito": cuántos carritos caben en el puesto de esta entrada.
    @Column(name = "capacidad_carritos")
    private Integer capacidadCarritos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio", nullable = false)
    private Condominio condominio;
}
