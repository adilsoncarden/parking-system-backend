package com.condosaas.api.module.log_prestamo_carrito.model;

import com.condosaas.api.module.carrito_carga.model.CarritoCarga;
import com.condosaas.api.module.entrada.model.Entrada;
import com.condosaas.api.module.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_prestamo_carrito")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogPrestamoCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log_prestamo")
    private Long id;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDateTime fechaPrestamo;

    @Column(name = "fecha_devolucion")
    private LocalDateTime fechaDevolucion;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "tiempo_limite_minutos")
    private Integer tiempoLimiteMinutos;

    @Column(name = "tiempo_excedido_minutos")
    private Integer tiempoExcedidoMinutos;

    @Column(name = "monto_penalizacion", precision = 12, scale = 2)
    private BigDecimal montoPenalizacion;

    @Builder.Default
    @Column(name = "penalizado", columnDefinition = "boolean default false")
    private Boolean penalizado = false;

    @Builder.Default
    @Column(name = "pagado", columnDefinition = "boolean default true")
    private Boolean pagado = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPrestamo estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito", nullable = false)
    private CarritoCarga carrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Por cuál entrada salió el carrito y por cuál se devolvió (puede ser distinta).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrada_salida")
    private Entrada entradaSalida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrada_devolucion")
    private Entrada entradaDevolucion;
}
