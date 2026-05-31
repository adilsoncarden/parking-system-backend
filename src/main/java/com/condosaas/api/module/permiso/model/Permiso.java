package com.condosaas.api.module.permiso.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permiso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 80)
    private String nombre;
}
