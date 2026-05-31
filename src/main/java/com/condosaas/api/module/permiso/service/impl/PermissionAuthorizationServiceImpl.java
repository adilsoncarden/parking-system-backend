package com.condosaas.api.module.permiso.service.impl;

import com.condosaas.api.module.permiso.repository.PermisoRepository;
import com.condosaas.api.module.permiso.repository.RolPermisoRepository;
import com.condosaas.api.module.permiso.service.PermissionAuthorizationService;
import com.condosaas.api.module.usuario.model.Usuario;
import com.condosaas.api.module.usuario.repository.UsuarioRepository;
import com.condosaas.api.security.PermisoCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PermissionAuthorizationServiceImpl implements PermissionAuthorizationService {

    private final RolPermisoRepository rolPermisoRepository;
    private final PermisoRepository permisoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public boolean isAdmin(String rolNombre) {
        return rolNombre != null && PermisoCatalog.ROL_ADMIN.equalsIgnoreCase(rolNombre.trim());
    }

    @Override
    public List<String> resolvePermisosForRol(Long rolId, String rolNombre) {
        if (isAdmin(rolNombre)) {
            return permisoRepository.findAllByOrderByNombreAsc().stream()
                    .map(p -> p.getNombre())
                    .toList();
        }
        return rolPermisoRepository.findPermisoNombresByRolId(rolId);
    }

    @Override
    public List<String> resolvePermisosForEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return resolvePermisosForRol(usuario.getRol().getId(), usuario.getRol().getNombre());
    }
}
