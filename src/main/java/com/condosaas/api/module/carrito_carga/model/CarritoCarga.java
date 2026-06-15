package com.condosaas.api.module.carrito_carga.model;

import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.entrada.model.Entrada;
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

    // Entrada (puerta) a la que está fijo el carrito. No se cambia de entrada:
    // se saca y se devuelve siempre por la misma. Nullable por compatibilidad con datos previos.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrada")
    private Entrada entrada;
}