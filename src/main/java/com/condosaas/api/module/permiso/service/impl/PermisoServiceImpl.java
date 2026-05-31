package com.condosaas.api.module.permiso.service.impl;

import com.condosaas.api.module.permiso.dto.*;
import com.condosaas.api.module.permiso.model.Permiso;
import com.condosaas.api.module.permiso.model.RolPermiso;
import com.condosaas.api.module.permiso.repository.PermisoRepository;
import com.condosaas.api.module.permiso.repository.RolPermisoRepository;
import com.condosaas.api.module.permiso.service.PermisoService;
import com.condosaas.api.module.permiso.service.PermissionAuthorizationService;
import com.condosaas.api.module.rol.model.Rol;
import com.condosaas.api.module.rol.repository.RolRepository;
import com.condosaas.api.security.PermisoCatalog;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PermisoServiceImpl implements PermisoService {

    private final PermisoRepository permisoRepository;
    private final RolPermisoRepository rolPermisoRepository;
    private final RolRepository rolRepository;
    private final PermissionAuthorizationService permissionAuthorizationService;

    @Override
    public PermisoResponseDTO create(PermisoRequestDTO dto) {
        if (permisoRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("El permiso ya existe");
        }
        Permiso entity = Permiso.builder().nombre(dto.getNombre().trim().toUpperCase()).build();
        return mapToDTO(permisoRepository.save(entity));
    }

    @Override
    public PermisoResponseDTO getById(Long id) {
        return mapToDTO(permisoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permiso no encontrado")));
    }

    @Override
    public List<PermisoResponseDTO> getAll() {
        return permisoRepository.findAllByOrderByNombreAsc().stream().map(this::mapToDTO).toList();
    }

    @Override
    public PermisoResponseDTO update(Long id, PermisoRequestDTO dto) {
        Permiso entity = permisoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permiso no encontrado"));
        entity.setNombre(dto.getNombre().trim().toUpperCase());
        return mapToDTO(permisoRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!permisoRepository.existsById(id)) {
            throw new EntityNotFoundException("Permiso no encontrado");
        }
        rolPermisoRepository.deleteByPermiso_Id(id);
        permisoRepository.deleteById(id);
    }

    @Override
    public RolPermisosResponseDTO getPermisosByRol(Long rolId) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
        List<String> nombres = permissionAuthorizationService.resolvePermisosForRol(rol.getId(), rol.getNombre());
        List<Permiso> all = permisoRepository.findAllByOrderByNombreAsc();
        List<Long> ids = all.stream()
                .filter(p -> nombres.contains(p.getNombre()))
                .map(Permiso::getId)
                .toList();
        return RolPermisosResponseDTO.builder()
                .rolId(rol.getId())
                .rolNombre(rol.getNombre())
                .permisoIds(ids)
                .permisoNombres(nombres)
                .build();
    }

    @Override
    public RolPermisosResponseDTO assignPermisosToRol(Long rolId, RolPermisosAssignDTO dto) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        if (permissionAuthorizationService.isAdmin(rol.getNombre())) {
            throw new IllegalArgumentException("El rol ADMIN tiene todos los permisos automáticamente");
        }

        rolPermisoRepository.deleteByRolId(rolId);

        List<Long> permisoIds = dto.getPermisoIds() != null ? dto.getPermisoIds() : List.of();
        List<RolPermiso> nuevos = new ArrayList<>();
        for (Long permisoId : permisoIds) {
            Permiso permiso = permisoRepository.findById(permisoId)
                    .orElseThrow(() -> new EntityNotFoundException("Permiso no encontrado: " + permisoId));
            nuevos.add(RolPermiso.builder().rol(rol).permiso(permiso).build());
        }
        rolPermisoRepository.saveAll(nuevos);

        List<String> nombres = nuevos.stream().map(rp -> rp.getPermiso().getNombre()).toList();
        return RolPermisosResponseDTO.builder()
                .rolId(rol.getId())
                .rolNombre(rol.getNombre())
                .permisoIds(permisoIds)
                .permisoNombres(nombres)
                .build();
    }

    private PermisoResponseDTO mapToDTO(Permiso entity) {
        return PermisoResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .build();
    }
}
