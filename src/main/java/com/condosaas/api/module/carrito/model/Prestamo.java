package com.condosaas.api.module.carrito.model;

import com.condosaas.api.module.acceso.model.EntradaSalida;
import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.enums.EstadoPrestamo;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prestamo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPrestamo estado;

    @Column(name = "hora_fin")
    private LocalDateTime horaFin;

    @Column(name = "hora_inicio", nullable = false)
    private LocalDateTime horaInicio;

    @Column(name = "monto_multa")
    private Double montoMulta;

    @Column(nullable = false)
    private Boolean multado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apartamento", nullable = false)
    private Apartamento apartamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito", nullable = false)
    private Carrito carrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrada_salida", nullable = false)
    private EntradaSalida entradaSalida;
}
