package com.condosaas.api.module.torre.model;

import com.condosaas.api.module.condominio.model.Condominio;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "torre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Torre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_torres")
    private Long idTorres;

    private String nombre;

    @Column(name = "cantidad_pisos")
    private Integer cantidadPisos;

    @Column(name = "cantidad_apartamentos")
    private Integer cantidadApartamentos;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominios")
    private Condominio condominio;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
