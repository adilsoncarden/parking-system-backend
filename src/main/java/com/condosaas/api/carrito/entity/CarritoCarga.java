package com.condosaas.api.carrito.entity;

import com.condosaas.api.entrada.entity.EntradaSalida;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrito_carga")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoCarga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Boolean disponible = true;

    @ManyToOne
    @JoinColumn(name = "id_entrada_salida", nullable = false)
    private EntradaSalida entradaSalida;
}