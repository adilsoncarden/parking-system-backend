package com.condosaas.api.config;

import com.condosaas.api.module.permiso.model.Permiso;
import com.condosaas.api.module.permiso.repository.PermisoRepository;
import com.condosaas.api.module.rol.model.Rol;
import com.condosaas.api.module.rol.repository.RolRepository;
import com.condosaas.api.security.PermisoCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PermissionDataInitializer implements ApplicationRunner {

    private final PermisoRepository permisoRepository;
    private final RolRepository rolRepository;

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
    }
}
