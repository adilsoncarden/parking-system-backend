package com.condosaas.api.module.piso.service.impl;

import com.condosaas.api.module.piso.dto.*;
import com.condosaas.api.module.piso.model.*;
import com.condosaas.api.module.piso.repository.PisoRepository;
import com.condosaas.api.module.piso.service.PisoService;
import com.condosaas.api.module.torre.model.Torre;
import com.condosaas.api.module.torre.repository.TorreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PisoServiceImpl implements PisoService {

    private final PisoRepository repository;
    private final TorreRepository torreRepository;

    @Override
    public PisoResponseDTO create(PisoRequestDTO dto) {

        Torre torre = torreRepository.findById(dto.getTorreId())
                .orElseThrow(() -> new EntityNotFoundException("Torre no encontrada"));

        Piso entity = Piso.builder()
                .numero(dto.getNumero())
                .estado(dto.getEstado())
                .torre(torre)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public PisoResponseDTO getById(Long id) {
        Piso entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Piso no encontrado"));

        return mapToDTO(entity);
    }

    @Override
    public List<PisoResponseDTO> getAll(Long torreId) {

        List<Piso> lista;

        if (torreId != null) {
            lista = repository.findByTorreId(torreId);
        } else {
            lista = repository.findAll();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public PisoResponseDTO update(Long id, PisoRequestDTO dto) {

        Piso entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Piso no encontrado"));

        Torre torre = torreRepository.findById(dto.getTorreId())
                .orElseThrow(() -> new EntityNotFoundException("Torre no encontrada"));

        entity.setNumero(dto.getNumero());
        entity.setEstado(dto.getEstado());
        entity.setTorre(torre);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Piso no encontrado");
        }
        repository.deleteById(id);
    }

    private PisoResponseDTO mapToDTO(Piso entity) {
        return PisoResponseDTO.builder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .estado(entity.getEstado())
                .torreId(entity.getTorre().getId())
                .torreNombre(entity.getTorre().getNombre())
                .condominioId(entity.getTorre().getCondominio().getId())
                .build();
    }
}