package com.condosaas.api.torres.entity;


import com.condosaas.api.condominio.entity.Condominio;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "torres")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Torres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTorres;

    @Column(nullable = false, length = 100)
    private String Nombre;

    @Column(name = "Cantidad_pisos")
    private Integer cantidadPisos;

    @Column(name = "Cantidad_apartamentos")
    private Integer cantidadApartamentos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominios", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "torres"})
    private Condominio condominio;

    @Column(name = "created_at", updatable = false)
    private java.time.LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = java.time.LocalDateTime.now();

    }
}