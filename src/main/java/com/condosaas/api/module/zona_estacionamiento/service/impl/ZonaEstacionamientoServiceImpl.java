package com.condosaas.api.module.zona_estacionamiento.service.impl;

import com.condosaas.api.module.zona_estacionamiento.dto.*;
import com.condosaas.api.module.zona_estacionamiento.model.*;
import com.condosaas.api.module.zona_estacionamiento.repository.ZonaEstacionamientoRepository;
import com.condosaas.api.module.zona_estacionamiento.service.ZonaEstacionamientoService;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ZonaEstacionamientoServiceImpl implements ZonaEstacionamientoService {

    private final ZonaEstacionamientoRepository repository;
    private final CondominioRepository condominioRepository;

    @Override
    public ZonaEstacionamientoResponseDTO create(ZonaEstacionamientoRequestDTO dto) {

        Condominio condominio = condominioRepository.findById(dto.getCondominioId())
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        ZonaEstacionamiento entity = ZonaEstacionamiento.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .estado(dto.getEstado())
                .condominio(condominio)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public ZonaEstacionamientoResponseDTO getById(Long id) {
        ZonaEstacionamiento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Zona no encontrada"));

        return mapToDTO(entity);
    }

    @Override
    public List<ZonaEstacionamientoResponseDTO> getAll(Long condominioId) {

        List<ZonaEstacionamiento> lista;

        if (condominioId != null) {
            lista = repository.findByCondominioId(condominioId);
        } else {
            lista = repository.findAll();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public ZonaEstacionamientoResponseDTO update(Long id, ZonaEstacionamientoRequestDTO dto) {

        ZonaEstacionamiento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Zona no encontrada"));

        Condominio condominio = condominioRepository.findById(dto.getCondominioId())
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setEstado(dto.getEstado());
        entity.setCondominio(condominio);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Zona no encontrada");
        }
        repository.deleteById(id);
    }

    private ZonaEstacionamientoResponseDTO mapToDTO(ZonaEstacionamiento entity) {
        return ZonaEstacionamientoResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .condominioId(entity.getCondominio().getId())
                .condominioNombre(entity.getCondominio().getNombre())
                .build();
    }
}