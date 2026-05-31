package com.condosaas.api.module.permiso.model;

import com.condosaas.api.module.rol.model.Rol;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rol_permiso", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_rol", "id_permiso" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolPermiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol_permiso")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_permiso", nullable = false)
    private Permiso permiso;
}
