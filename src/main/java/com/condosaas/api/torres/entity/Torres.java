package com.condosaas.api.torres.entity;


import com.condosaas.api.Pisos.entity.Pisos;
import com.condosaas.api.condominio.entity.Condominio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "torres")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Torres {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private Long id;

    @Column(nullable = false, length = 100)
    private String Nombre;

    @Column(name = "Cantidad_pisos")
    private Integer cantidadPisos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominios", nullable = false)
    private Condominio condominio;

    @OneToMany(mappedBy = "torre", cascade = CascadeType.ALL)
    private List<Pisos> pisos;


    @Column(name = "created_at", updatable = false)
    private java.time.LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = java.time.LocalDateTime.now();

    }
}