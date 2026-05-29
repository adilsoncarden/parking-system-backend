package com.condosaas.api.module.parkcontrol.plaza.model;

import com.condosaas.api.module.enums.EstadoOcupacion;
import com.condosaas.api.module.parkcontrol.zona.model.Zona;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plaza")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plaza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @Enumerated(EnumType.STRING)
    private EstadoOcupacion estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zona")
    private Zona zona;
}
