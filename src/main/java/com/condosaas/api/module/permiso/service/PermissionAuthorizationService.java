package com.condosaas.api.module.permiso.service;

import java.util.List;

public interface PermissionAuthorizationService {

    List<String> resolvePermisosForRol(Long rolId, String rolNombre);

    List<String> resolvePermisosForEmail(String email);

    boolean isAdmin(String rolNombre);
}
