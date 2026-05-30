package com.condosaas.api.module.carrito_carga.model;

import com.condosaas.api.module.condominio.model.Condominio;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carrito_carga")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarritoCarga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCarrito estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio", nullable = false)
    private Condominio condominio;
}