package com.condosaas.api.module.carrito.model;

import com.condosaas.api.module.acceso.model.EntradaSalida;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carrito_carga")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean disponible;

    @Column(nullable = false, length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrada_salida", nullable = false)
    private EntradaSalida entradaSalida;
}
