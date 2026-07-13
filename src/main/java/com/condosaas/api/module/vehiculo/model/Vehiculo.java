package com.condosaas.api.module.vehiculo.model;

import com.condosaas.api.module.usuario.model.TipoOcupante;
import com.condosaas.api.module.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehiculo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Long id;

    @Column(name = "placa", nullable = false, unique = true)
    private String placa;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "color")
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoVehiculo estado;

    // Tipo de ocupante del vehículo. Para carros de VISITANTE (sin dueño residente)
    // este campo lo identifica; en carros de residentes se toma del usuario dueño.
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ocupante")
    private TipoOcupante tipoOcupante;

    // Dueño residente del vehículo. NULLABLE: los carros de visitantes no tienen dueño.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}