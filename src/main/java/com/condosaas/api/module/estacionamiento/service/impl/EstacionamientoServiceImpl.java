package com.condosaas.api.module.estacionamiento.service.impl;

import com.condosaas.api.module.estacionamiento.dto.*;
import com.condosaas.api.module.estacionamiento.model.*;
import com.condosaas.api.module.estacionamiento.repository.EstacionamientoRepository;
import com.condosaas.api.module.estacionamiento.service.EstacionamientoService;
import com.condosaas.api.module.zona_estacionamiento.model.ZonaEstacionamiento;
import com.condosaas.api.module.zona_estacionamiento.repository.ZonaEstacionamientoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstacionamientoServiceImpl implements EstacionamientoService {

    private final EstacionamientoRepository repository;
    private final ZonaEstacionamientoRepository zonaRepository;

    @Override
    public EstacionamientoResponseDTO create(EstacionamientoRequestDTO dto) {

        ZonaEstacionamiento zona = zonaRepository.findById(dto.getZonaEstacionamientoId())
                .orElseThrow(() -> new EntityNotFoundException("Zona no encontrada"));

        Estacionamiento entity = Estacionamiento.builder()
                .codigo(dto.getCodigo())
                .estadoOcupacion(dto.getEstadoOcupacion())
                .zona(zona)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public EstacionamientoResponseDTO getById(Long id) {
        Estacionamiento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estacionamiento no encontrado"));

        return mapToDTO(entity);
    }

    @Override
    public List<EstacionamientoResponseDTO> getAll(Long zonaId) {

        List<Estacionamiento> lista;

        if (zonaId != null) {
            lista = repository.findByZonaId(zonaId);
        } else {
            lista = repository.findAll();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public EstacionamientoResponseDTO update(Long id, EstacionamientoRequestDTO dto) {

        Estacionamiento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estacionamiento no encontrado"));

        ZonaEstacionamiento zona = zonaRepository.findById(dto.getZonaEstacionamientoId())
                .orElseThrow(() -> new EntityNotFoundException("Zona no encontrada"));

        entity.setCodigo(dto.getCodigo());
        entity.setEstadoOcupacion(dto.getEstadoOcupacion());
        entity.setZona(zona);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Estacionamiento no encontrado");
        }
        repository.deleteById(id);
    }

    private EstacionamientoResponseDTO mapToDTO(Estacionamiento entity) {
        return EstacionamientoResponseDTO.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .estadoOcupacion(entity.getEstadoOcupacion())
                .zonaEstacionamientoId(entity.getZona().getId())
                .zonaNombre(entity.getZona().getNombre())
                .condominioId(entity.getZona().getCondominio().getId())
                .build();
    }
}