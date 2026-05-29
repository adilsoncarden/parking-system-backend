package com.condosaas.api.module.apartamento.model;

import com.condosaas.api.module.enums.EstadoApartamento;
import com.condosaas.api.module.piso.model.Piso;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "apartamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Apartamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EstadoApartamento estado;

    @Column(name = "numero_apartamento", nullable = false, length = 20)
    private String numeroApartamento;

    @Column(length = 100)
    private String propietario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_piso", nullable = false)
    private Piso piso;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoApartamento.DISPONIBLE;
        }
    }
}
