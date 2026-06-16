package com.condosaas.api.config;

import com.condosaas.api.module.permiso.model.Permiso;
import com.condosaas.api.module.permiso.model.RolPermiso;
import com.condosaas.api.module.permiso.repository.PermisoRepository;
import com.condosaas.api.module.permiso.repository.RolPermisoRepository;
import com.condosaas.api.module.rol.model.Rol;
import com.condosaas.api.module.rol.repository.RolRepository;
import com.condosaas.api.security.PermisoCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PermissionDataInitializer implements ApplicationRunner {

    private final PermisoRepository permisoRepository;
    private final RolRepository rolRepository;
    private final RolPermisoRepository rolPermisoRepository;

    // Permisos mínimos de gestión de usuarios (módulo CONFIGURACION) que SIEMPRE deben
    // tener los administradores de condominio y sus asistentes, en todos los condominios.
    // El profesor pidió que los administradores puedan crear y modificar usuarios.
    // (El scoping por condominio lo aplica CurrentUser, así que cada admin solo gestiona
    // el personal de SU condominio.)
    private static final List<String> GESTION_USUARIOS = List.of(
            PermisoCatalog.VER_CONFIGURACION,
            PermisoCatalog.CREAR_CONFIGURACION,
            PermisoCatalog.EDITAR_CONFIGURACION
    );

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        for (String nombre : PermisoCatalog.all()) {
            if (!permisoRepository.existsByNombre(nombre)) {
                permisoRepository.save(Permiso.builder().nombre(nombre).build());
            }
        }

        if (!rolRepository.existsByNombreIgnoreCase(PermisoCatalog.ROL_ADMIN)) {
            rolRepository.save(Rol.builder()
                    .nombre(PermisoCatalog.ROL_ADMIN)
                    .descripcion("Acceso total al sistema")
                    .build());
        }

        // Garantiza (idempotente) que los administradores puedan gestionar usuarios.
        ensureRolePermisos("ADMIN_CONDOMINIO", GESTION_USUARIOS);
        ensureRolePermisos("SUBADMIN", GESTION_USUARIOS);
    }

    // Asigna los permisos indicados al rol si aún no los tiene (sin tocar el resto).
    private void ensureRolePermisos(String nombreRol, List<String> permisos) {
        Rol rol = rolRepository.findByNombre(nombreRol).orElse(null);
        if (rol == null) {
            return; // El rol todavía no existe en esta BD; nada que asignar.
        }

        Set<String> actuales = new HashSet<>(
                rolPermisoRepository.findPermisoNombresByRolId(rol.getId()));

        for (String nombrePermiso : permisos) {
            if (actuales.contains(nombrePermiso)) {
                continue;
            }
            permisoRepository.findByNombre(nombrePermiso).ifPresent(permiso ->
                    rolPermisoRepository.save(RolPermiso.builder()
                            .rol(rol)
                            .permiso(permiso)
                            .build()));
        }
    }
}
