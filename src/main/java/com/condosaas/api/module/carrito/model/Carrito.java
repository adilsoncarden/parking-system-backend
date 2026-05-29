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

    private Boolean disponible;
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrada_salida")
    private EntradaSalida entradaSalida;
}
