package com.condosaas.api.module.rol.service.impl;

import com.condosaas.api.module.rol.dto.*;
import com.condosaas.api.module.rol.model.Rol;
import com.condosaas.api.module.rol.repository.RolRepository;
import com.condosaas.api.module.rol.service.RolService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RolServiceImpl implements RolService {

    private final RolRepository repository;

    @Override
    public RolResponseDTO create(RolRequestDTO dto) {
        Rol rol = Rol.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();

        return mapToDTO(repository.save(rol));
    }

    @Override
    public RolResponseDTO getById(Long id) {
        Rol rol = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        return mapToDTO(rol);
    }

    @Override
    public List<RolResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public RolResponseDTO update(Long id, RolRequestDTO dto) {
        Rol rol = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        rol.setNombre(dto.getNombre());
        rol.setDescripcion(dto.getDescripcion());

        return mapToDTO(repository.save(rol));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Rol no encontrado");
        }
        repository.deleteById(id);
    }

    private RolResponseDTO mapToDTO(Rol rol) {
        return RolResponseDTO.builder()
                .id(rol.getId())
                .nombre(rol.getNombre())
                .descripcion(rol.getDescripcion())
                .build();
    }
}