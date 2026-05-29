package com.condosaas.api.prestamo.entity;

import com.condosaas.api.apartamento.entity.Apartamento;
import com.condosaas.api.carrito.entity.CarritoCarga;
import com.condosaas.api.entrada.entity.EntradaSalida;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "prestamo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_carrito", nullable = false)
    private CarritoCarga carrito;

    @ManyToOne
    @JoinColumn(name = "id_apartamento", nullable = false)
    private Apartamento apartamento;

    @ManyToOne
    @JoinColumn(name = "id_entrada_salida", nullable = false)
    private EntradaSalida entradaSalida;

    @Column(name = "hora_inicio", nullable = false)
    private LocalDateTime horaInicio;

    @Column(name = "hora_fin")
    private LocalDateTime horaFin;

    @Column(nullable = false, length = 20)
    private String estado; // activo, devuelto, multado

    @Column(nullable = false)
    private Boolean multado = false;

    @Column(name = "monto_multa")
    private Double montoMulta;
}