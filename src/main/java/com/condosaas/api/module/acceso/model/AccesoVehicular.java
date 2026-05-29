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

    private LocalDateTime hora;
    private String observaciones;
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento")
    private TipoRegistro tipoMovimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio")
    private Condominio condominio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_punto_acceso")
    private EntradaSalida puntoAcceso;
}
