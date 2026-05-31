package com.condosaas.api.module.detalle_plaza_parkcontrol.service.impl;

import com.condosaas.api.module.detalle_plaza_parkcontrol.dto.*;
import com.condosaas.api.module.detalle_plaza_parkcontrol.model.*;
import com.condosaas.api.module.detalle_plaza_parkcontrol.repository.DetallePlazaParkControlRepository;
import com.condosaas.api.module.detalle_plaza_parkcontrol.service.DetallePlazaService;
import com.condosaas.api.module.estacionamiento.model.Estacionamiento;
import com.condosaas.api.module.estacionamiento.repository.EstacionamientoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DetallePlazaServiceImpl implements DetallePlazaService {

    private final DetallePlazaParkControlRepository repository;
    private final EstacionamientoRepository estacionamientoRepository;

    @Override
    public DetallePlazaResponseDTO create(DetallePlazaRequestDTO dto) {

        Estacionamiento estacionamiento = estacionamientoRepository.findById(dto.getEstacionamientoId())
                .orElseThrow(() -> new EntityNotFoundException("Estacionamiento no encontrado"));

        repository.findByEstacionamientoId(dto.getEstacionamientoId())
                .ifPresent(e -> {
                    throw new IllegalStateException("El estacionamiento ya tiene un detalle asignado");
                });

        DetallePlazaParkControl entity = DetallePlazaParkControl.builder()
                .tipo(dto.getTipo())
                .numeroPlaza(dto.getNumeroPlaza())
                .observaciones(dto.getObservaciones())
                .estadoRegistro(dto.getEstadoRegistro())
                .estacionamiento(estacionamiento)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public DetallePlazaResponseDTO getById(Long id) {
        DetallePlazaParkControl entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Detalle de plaza no encontrado"));

        return mapToDTO(entity);
    }

    @Override
    public List<DetallePlazaResponseDTO> getAll(Long estacionamientoId) {

        List<DetallePlazaParkControl> lista = repository.findAll();

        if (estacionamientoId != null) {
            lista = lista.stream()
                    .filter(e -> e.getEstacionamiento().getId().equals(estacionamientoId))
                    .toList();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public DetallePlazaResponseDTO update(Long id, DetallePlazaRequestDTO dto) {

        DetallePlazaParkControl entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Detalle no encontrado"));

        Estacionamiento estacionamiento = estacionamientoRepository.findById(dto.getEstacionamientoId())
                .orElseThrow(() -> new EntityNotFoundException("Estacionamiento no encontrado"));

        entity.setTipo(dto.getTipo());
        entity.setNumeroPlaza(dto.getNumeroPlaza());
        entity.setObservaciones(dto.getObservaciones());
        entity.setEstadoRegistro(dto.getEstadoRegistro());
        entity.setEstacionamiento(estacionamiento);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Detalle no encontrado");
        }
        repository.deleteById(id);
    }

    private DetallePlazaResponseDTO mapToDTO(DetallePlazaParkControl entity) {
        return DetallePlazaResponseDTO.builder()
                .id(entity.getId())
                .tipo(entity.getTipo())
                .numeroPlaza(entity.getNumeroPlaza())
                .observaciones(entity.getObservaciones())
                .estadoRegistro(entity.getEstadoRegistro())
                .estacionamientoId(entity.getEstacionamiento().getId())
                .codigoEstacionamiento(entity.getEstacionamiento().getCodigo())
                .zonaId(entity.getEstacionamiento().getZona().getId())
                .build();
    }
}