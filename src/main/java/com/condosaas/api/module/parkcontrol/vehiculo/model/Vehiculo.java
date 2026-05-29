package com.condosaas.api.module.parkcontrol.vehiculo.model;

import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.condominio.model.Condominio;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehiculo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;
    private String marca;
    private String modelo;
    private String placa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apartamento")
    private Apartamento apartamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio")
    private Condominio condominio;
}
