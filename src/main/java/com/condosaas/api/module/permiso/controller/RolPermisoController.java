package com.condosaas.api.module.permiso.controller;

import com.condosaas.api.module.permiso.dto.RolPermisosAssignDTO;
import com.condosaas.api.module.permiso.dto.RolPermisosResponseDTO;
import com.condosaas.api.module.permiso.service.PermisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolPermisoController {

    private final PermisoService permisoService;

    @GetMapping("/{id}/permisos")
    public ResponseEntity<RolPermisosResponseDTO> getPermisosByRol(@PathVariable Long id) {
        return ResponseEntity.ok(permisoService.getPermisosByRol(id));
    }

    @PutMapping("/{id}/permisos")
    public ResponseEntity<RolPermisosResponseDTO> assignPermisos(
            @PathVariable Long id,
            @RequestBody RolPermisosAssignDTO dto) {
        return ResponseEntity.ok(permisoService.assignPermisosToRol(id, dto));
    }
}
