package com.condosaas.api.module.acceso.model;

import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.enums.TipoRegistro;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "acceso_vehicular")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccesoVehicular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime hora;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(nullable = false, length = 15)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false, length = 10)
    private TipoRegistro tipoMovimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio", nullable = false)
    private Condominio condominio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_punto_acceso")
    private EntradaSalida puntoAcceso;
}
