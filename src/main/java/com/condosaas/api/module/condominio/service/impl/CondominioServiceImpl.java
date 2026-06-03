package com.condosaas.api.module.condominio.service.impl;

import com.condosaas.api.module.condominio.dto.*;
import com.condosaas.api.module.condominio.model.*;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.condominio.service.CondominioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CondominioServiceImpl implements CondominioService {

    private final CondominioRepository repository;

    @Override
    public CondominioResponseDTO create(CondominioRequestDTO dto) {

        Condominio entity = Condominio.builder()
                .nombre(dto.getNombre())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public CondominioResponseDTO getById(Long id) {
        Condominio entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        return mapToDTO(entity);
    }

    @Override
    public List<CondominioResponseDTO> getAll() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public CondominioResponseDTO update(Long id, CondominioRequestDTO dto) {

        Condominio entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        entity.setNombre(dto.getNombre());
        entity.setDireccion(dto.getDireccion());
        entity.setTelefono(dto.getTelefono());
        entity.setEmail(dto.getEmail());

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Condominio no encontrado");
        }
        repository.deleteById(id);
    }

    private CondominioResponseDTO mapToDTO(Condominio entity) {
        return CondominioResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .direccion(entity.getDireccion())
                .telefono(entity.getTelefono())
                .email(entity.getEmail())
                .build();
    }
}
