package com.condosaas.api.module.usuario.model;

import com.condosaas.api.module.rol.model.Rol;
import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.entrada.model.Entrada;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nombres", nullable = false)
    private String nombres;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ocupante", nullable = false)
    private TipoOcupante tipoOcupante;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoUsuario estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apartamento")
    private Apartamento apartamento;

    // Condominio al que está ligado un admin de condominio (null para el superadmin).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio")
    private Condominio condominio;

    // Solo para porteros: entrada (puerta) que cubren y turno (día/noche). Null en otros roles.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrada")
    private Entrada entrada;

    @Enumerated(EnumType.STRING)
    @Column(name = "turno")
    private Turno turno;
}