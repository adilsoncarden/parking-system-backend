package com.condosaas.api.module.apartamento.model;

import com.condosaas.api.module.enums.EstadoApartamento;
import com.condosaas.api.module.piso.model.Piso;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "apartamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Apartamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private EstadoApartamento estado;

    @Column(name = "numero_apartamento")
    private String numeroApartamento;

    private String propietario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_piso")
    private Piso piso;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
